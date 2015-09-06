package com.kingscastle.framework.implementation;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class AndroidFileIO{
	@NonNull
    private final Context context;
	private final AssetManager assets;
	@NonNull
    private final String externalStoragePath;



	public AndroidFileIO( @NonNull Context context )
	{
		this.context = context;
		this.assets = context.getAssets();
		this.externalStoragePath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + File.separator;
	}



	@NonNull
    public InputStream readAsset(String file) throws IOException
	{
		return assets.open(file);
	}



	@Nullable
    public BufferedReader readFile(@NonNull File dir,String file) throws IOException
	{
		File[] files = dir.listFiles();
		for( File f: files )
		{
			if( f.getName().equals(file) )
			{

			}
			return new BufferedReader( new FileReader(f) );
		}
		return null;
	}


	@NonNull
    public BufferedReader readFile( @NonNull File f ) throws FileNotFoundException
	{
		return new BufferedReader( new FileReader( f ) );
	}



	@Nullable
    public InputStream readFileInputStream(@NonNull File dir,String file) throws IOException
	{
		File[] files = dir.listFiles();
		for(File f: files){
			if(f.getName().equals(file)){
				return new BufferedInputStream(new FileInputStream(f));
			}
		}
		return null;
	}


	@NonNull
    public InputStream readFileInputStream(@NonNull File f) throws FileNotFoundException
	{
		return new BufferedInputStream(new FileInputStream(f));
	}

	@NonNull
    public BufferedWriter writeFile( @NonNull File file ) throws IOException
	{

		if( !file.exists() )
		{
			file.createNewFile();
		}

		return new BufferedWriter( new FileWriter( file ) );
	}


	@NonNull
    public BufferedWriter writeFile(File dir, @NonNull String file) throws IOException
	{
		try{
			File f = new File(dir,file);
			if(!f.exists()) {
				f.createNewFile();
			}
			return new BufferedWriter(new FileWriter(f));
		}catch(FileNotFoundException fe){
		}
		File f = new File(dir,file);
		f.createNewFile();
		return new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
	}


	public SharedPreferences getSharedPref()
	{
		return PreferenceManager.getDefaultSharedPreferences(context);
	}




}