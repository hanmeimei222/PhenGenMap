package com.init.dataLoad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.global.GlobalData;
import com.model.PNode;

@Repository
public class PhenoDataLoad {
	//根据读入的一行新建一个节点
	private PNode makeNodeByString(String str)
	{
		String []templine = str.split("\t");
		PNode pd = new PNode();
		Map<PNode,Boolean>father = new HashMap<PNode,Boolean>();
		Map<PNode,Boolean>son = new HashMap<PNode,Boolean>();
		pd.setPheno_id(templine[0]);
		pd.setPheno_name(templine[1]);
		pd.setPheno_def(templine[2]);
		pd.setPheno_level(templine[3]);
		pd.setFather(father);
		pd.setSon(son);

		return pd;
	}

	//从phen_info.txt中读入所有表型节点的信息，构建id-PNode、level-PNodes和name-id
	private void readPNodes(String infile,Map<String,PNode> allnodes,Map<String,Map<PNode,Boolean>>levelmap,Map<String,String>namemap){
		Map<String,String> linemap = new HashMap<String,String>();
		BufferedReader in=null;
		//把每个节点的信息放到map中
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(infile),"UTF-8"));
			String line="";

			//从文件中逐行读入放入Map<String id,String line>
			while(null!=(line= in.readLine())){
				String []temp = line.split("\t");
				linemap.put(temp[0], line);
			}

			List<PNode> currLevel = new ArrayList<PNode>();
			List<PNode> nexLevel = new ArrayList<PNode>();
			//c初始化当前层节点为0000001节点
			PNode node = makeNodeByString(linemap.get("MP:0000001"));
			currLevel.add(node);

			//将孤立节点如MP:0000002加入allnodes里面
			for (Map.Entry<String, String> entry : linemap.entrySet()) {
				String [] temp = entry.getValue().split("\t");

				if(temp[4].equals("#")&&temp[5].equals("#")){
					PNode pd = makeNodeByString(entry.getValue());
					currLevel.add(pd);

				}
			}

			while(currLevel.size()!=0)
			{
				//所有节点都会从currLevel里走一遍，所以对他进行遍历，构造出层数和该层包含哪些节点的Map

				for (PNode n : currLevel) {

					//添加name -- id map
					String name = n.getPheno_name();
					String id = n.getPheno_id();

					if(!namemap.containsKey(name)){
						namemap.put(name, id);
					}

					//添加 level -- nodes map
					String levels = n.getPheno_level();
					String level[]= levels.split(",");
					for (String l : level) {
						Map<PNode,Boolean>map  = levelmap.get(l);
						if( map == null)
						{
							map = new HashMap<PNode,Boolean>();
							levelmap.put(l, map);
						}
						map.put(n, true);
					}

					//根据当前节点的string：child去找到所有他的孩子节点

					allnodes.put(id, n);
					String [] info =linemap.get(n.getPheno_id()).split("\t");
					if(!info[5].equals("#")){
						String []sonId = info[5].split(",");
						for (String sid : sonId) {
							PNode child = makeNodeByString(linemap.get(sid));

							//对于他的孩子节点，把当前节点放入父亲属性
							child.getFather().put(n, true);
							//对于当前节点，把孩子节点放到孩子的属性中
							n.getSon().put(child, true);
							allnodes.put(sid, child);
							nexLevel.add(child);
						}
					}
					if(info[4].equals("#")&&info[5].equals("#")){
						allnodes.put(n.getPheno_id(), n);
					}
				}
				//更新当前节点集合为孩子节点集合
				currLevel.clear();
				currLevel.addAll(nexLevel);
				nexLevel.clear();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//加载表型数据
	public void loadPhenoData() {
		
		String infile = GlobalData.PATH+"/data/inter_data/phen_info.txt";
		//读取所有节点，只一次
		readPNodes(infile,GlobalData.allnodes,GlobalData.levelmap,GlobalData.namemap);
	}
}
