package com.init.dataLoad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.global.GlobalData;
import com.model.PPINode;

@Repository
public class PPIDataLoad {
	//加载gene数据
		public void loadPPIInteraction(String version) {
			String infile = GlobalData.PATH+"/data/input_data/"+version+"/BIOGRID-ORGANISM-Mus_musculus-3.2.113.tab2.txt";
			readBIOGRIDFile(infile);
		}

		private void readBIOGRIDFile(String file)
		{
			BufferedReader in=null;
			try {
				in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
				String line="";
				in.readLine();
				while(null!=(line= in.readLine()))
				{
					String [] tmp = line.split("\t");
					String ppi1 = tmp[1];
					String ppi2 = tmp[2].trim();
					//将节点放入到全局map中
					if(GlobalData.allppis.get(ppi1)==null)
					{
						GlobalData.allppis.put(ppi1, new PPINode(ppi1));
					}
					if(GlobalData.allppis.get(ppi2)==null)
					{
						GlobalData.allppis.put(ppi2, new PPINode(ppi2));
					}
					
					//存储节点间关联
					PPINode first = GlobalData.allppis.get(ppi1);
					PPINode second = GlobalData.allppis.get(ppi2);
					
					Map<PPINode,Boolean> firstMap = GlobalData.ppi_ppi_map.get(first);
					if(firstMap == null)
					{
						firstMap = new HashMap<PPINode, Boolean>();
						GlobalData.ppi_ppi_map.put(first, firstMap);
					}
					firstMap.put(second, true);
					
					Map<PPINode,Boolean> secondMap = GlobalData.ppi_ppi_map.get(second);
					if(secondMap == null)
					{
						secondMap = new HashMap<PPINode, Boolean>();
						GlobalData.ppi_ppi_map.put(second, secondMap);
					}
					secondMap.put(first, true);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
}
