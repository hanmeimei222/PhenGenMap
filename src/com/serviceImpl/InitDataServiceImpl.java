package com.serviceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.global.GlobalData;
import com.init.dataLoad.GPDataLoad;
import com.init.dataLoad.GenePathwayDataLoad;
import com.init.dataLoad.GenePPIDataLoad;
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
}
