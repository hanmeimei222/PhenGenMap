package com.serviceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.GlobalData;
import com.init.dataLoad.GPDataLoad;
import com.init.dataLoad.GenePPIDataLoad;
import com.init.dataLoad.GenePathwayDataLoad;
import com.init.dataLoad.PPIDataLoad;
import com.init.dataLoad.PhenoDataLoad;
import com.service.InitDataService;

@Service
public class InitDataServiceImpl implements InitDataService{

	@Autowired
	private PhenoDataLoad phenLoad;
	@Autowired
	private GenePathwayDataLoad geneLoad;

	@Autowired
	private GPDataLoad gpLoad;

	@Autowired 
	PPIDataLoad ppiLoad;

	@Autowired
	GenePPIDataLoad genePPILoad;

	@Override
	public void initData(){
		//获取服务器绝对路径
		GlobalData.PATH = System.getProperty("search.root")+"/";
		//获取数据版本信息

		GlobalData.dataVersions.addAll(getDataVersion(GlobalData.PATH+"/data/inter_data"));

		
		String latestVersion = GlobalData.dataVersions.get(GlobalData.dataVersions.size()-1);
		GlobalData.curVersion=latestVersion;
		loadData(latestVersion);
	}

	/**
	 * 加载数据和切换数据用
	 * @param version
	 */
	@Override
	public boolean loadData(String version)
	{
		try{
			//清理原来的数据
			cleanOldData();
			//初始化表型数据
			phenLoad.loadPhenoData(version);
			//初始化基因数据
			geneLoad.loadGeneData(version);
			//初始化基因-表型数据
			gpLoad.loadGPData(version);
			//初始化ppi关联数据
			ppiLoad.loadPPIInteraction(version);
			//初始化genePPI关联数据
			genePPILoad.loadGeneEntrezMapping(version);
			
			GlobalData.curVersion=version;
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}


	private List<String> getDataVersion(String path)
	{
		List<String> dataVersion = new ArrayList<String>();
		File file=new File(path);
		File[] tempList = file.listFiles();
		for (int i = 0; i < tempList.length; i++) {
			if (tempList[i].isDirectory()) {
				dataVersion.add(tempList[i].getName());
			}
		}
		return dataVersion;
	}
	
	
	private void cleanOldData()
	{
		//将globalData中存储的数据都清理掉;
		GlobalData.allpnodes.clear();
		GlobalData.levelmap.clear();
		GlobalData.namemap.clear();
		
		//基因全局数据
		GlobalData.allgnodes.clear();
		
		//pathway全局变量
		GlobalData.allways.clear();
		GlobalData.classmap.clear();
		GlobalData.pwnamemap.clear();
		GlobalData.clsmap .clear();
		GlobalData.relatedPathway .clear();

		
		//g-p全局数据
		GlobalData.g_p_map.clear();
		GlobalData.p_g_map.clear();
		
		
		//记录文件中包含的ppi的节点信息
		GlobalData.allppis.clear();
		//ppi交互数据
		GlobalData.ppi_ppi_map.clear();
		//g-ppi 关联数据
		GlobalData.gene_ppi_map.clear();
		GlobalData.ppi_gene_map.clear();
	}

	public List<String> getSourceFile(){
		List<String> filename = new ArrayList<String>();
		String path = GlobalData.PATH+"/data/input_data/"+GlobalData.curVersion;
		File file = new File(path);
		File[] tempList = file.listFiles();
		for(int i =0;i<tempList.length;i++){
			if(tempList[i].isFile()){
				filename.add("data/input_data/"+GlobalData.curVersion+"/"+tempList[i].getName());
			}
		}
		return filename;
	}
}
