package com.init.dataLoad;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.global.GlobalData;
import com.model.GNode;
import com.model.PNode;

@Repository
public class GPDataLoad {

	public void readGPAssociation(String infile){

		BufferedReader in=null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(infile),"UTF-8"));
			String line="";
			while(null!=(line= in.readLine())){
				String []temp = line.split("\t");

				//构造gene节点
				String symbol_name = temp[0].toUpperCase();
				GNode gn = GlobalData.allgnodes.get(symbol_name);
				

				//构造表型节点
				String mp_id = temp[1];
				PNode pn = GlobalData.allpnodes.get(mp_id);
				//去掉没有父亲没有孩子的单个表型节点（如MP:0000002），这些在allnodes里是没有的
				if(pn!=null){
					//构造Map<GNode, Map<PNode,Boolean>>g_p_map
					Map<PNode,Boolean>pmap = GlobalData.g_p_map.get(gn);
					if(pmap==null){
						pmap = new HashMap<PNode, Boolean>();
						GlobalData.g_p_map.put(gn, pmap);
					}
					pmap.put(pn,true);

					//构造Map<PNode, Map<GNode,Boolean>>p_g_map
					Map<GNode,Boolean>gmap = GlobalData.p_g_map.get(pn);
					if(gmap==null){
						gmap = new HashMap<GNode,Boolean>();
						GlobalData.p_g_map.put(pn, gmap);
					}
					gmap.put(gn, true);
				}
			}
		}  catch (Exception e) {
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


	public void loadGPData(String version ) {
		String infile = GlobalData.PATH+"/data/inter_data/"+version+"/symbol_mp_mgi.txt";
		
		GPDataLoad gpdl = new GPDataLoad();
		gpdl.readGPAssociation(infile);
		System.out.println();
	}
}
