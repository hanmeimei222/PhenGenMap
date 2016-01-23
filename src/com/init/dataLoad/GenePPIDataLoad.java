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
import com.model.PPINode;

@Repository
public class GenePPIDataLoad {

	//加载gene数据
	public void loadGeneEntrezMapping(String version) {
		String infile = GlobalData.PATH+"/data/input_data/"+version+"/MGI_Gene_Model_Coord.rpt.txt";
		readGeneCorrdFile(infile);
	}

	private void readGeneCorrdFile(String file)
	{
		BufferedReader in=null;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String line="";
			in.readLine();
			while(null!=(line= in.readLine()))
			{
				String [] tmp = line.split("\t");
				String symbol = tmp[2].toUpperCase();
				String EntrezId = tmp[5].trim();
				
				//如果未包含该gene节点，则把他加进去
				if(!GlobalData.allgnodes.containsKey(symbol))
				{
					GNode gn = new GNode();
					gn.setSymbol_name(symbol);
					GlobalData.allgnodes.put(symbol, gn);
				}
				
				//如果ppimap中不包含该节点，则加进去
				if(!GlobalData.allppis.containsKey(EntrezId))
				{
					PPINode ppiNode = new PPINode(EntrezId);
				
					GlobalData.allppis.put(EntrezId, ppiNode);
				}
				
				GNode gn = GlobalData.allgnodes.get(symbol);
				PPINode ppiNode = GlobalData.allppis.get(EntrezId);
				
				Map<PPINode,Boolean>PPI =  GlobalData.gene_ppi_map.get(gn);
				
				if(PPI == null)
				{
					PPI = new HashMap<PPINode, Boolean>();
					GlobalData.gene_ppi_map.put(gn,PPI);
				}
				PPI.put(ppiNode, true);
				
				
				Map<GNode,Boolean> geneMap =GlobalData.ppi_gene_map.get(ppiNode);
				if(geneMap == null)
				{
					geneMap = new HashMap<GNode, Boolean>();
					GlobalData.ppi_gene_map.put(ppiNode,geneMap);
				}
				geneMap.put(gn, true);
				
				
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
