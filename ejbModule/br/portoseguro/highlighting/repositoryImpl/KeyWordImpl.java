package br.portoseguro.highlighting.repositoryImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class KeyWordImpl {

	private String archive = ""+System.getProperty("user.dir")+"/conf.ini";
	
	public List<String> lerPalavrasChaves(){
		File conf = new File(archive);

		InputStreamReader inputStreamReader;
		BufferedReader bufferedReader;
		List<String> palavrasList = new ArrayList<String>();

		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(conf),"UTF-8");
			bufferedReader  = new BufferedReader(inputStreamReader);

			String linha = bufferedReader.readLine();

			while(linha != null){
				palavrasList.add(linha);
				linha = bufferedReader.readLine();
			}

			inputStreamReader.close();
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return palavrasList;
	}
}
