package com.cjeg.hfm;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对数据进行解码
 * @author acer
 *
 */
public class UHFM {
	public static void main(String[] args) {
		try {
			DataInputStream dataIn=new DataInputStream(new FileInputStream("dde.zips"));
			short length=dataIn.readShort();
			int dataSize=dataIn.readInt();
			Map<String,String>map=new HashMap<String,String>();
			//存放映射
			List<HFNode> hNode=new ArrayList<HFNode>();
			for(int i=0;i<length;i++){
				byte index=dataIn.readByte();
				byte code=dataIn.readByte();
				byte len=dataIn.readByte();
				String codes=byte2str(code,len);
				HFNode node=new HFNode();
				node.setIndex(index);
				node.setCode(codes);
				hNode.add(node);
			}
			byte[] data=new byte[dataSize];
			dataIn.read(data);
			dataIn.close();
			//将字节码转换成字符串
			StringBuffer buff=new StringBuffer();
			for(int i=0;i<dataSize;i++){
				buff.append(byte2str(data[i],8));
			}
			String byteCode=buff.toString();
			List<Byte> resultList=new ArrayList<Byte>();
			int lens=byteCode.length();
			int index=0;
			//开始解码
			do{
				for(int i=0;i<hNode.size();i++){
					HFNode node=hNode.get(i);
					String code=node.getCode();
					int lengths=byteCode.length();
					if(byteCode.startsWith(code)){
						System.out.println(lengths);
						byteCode=byteCode.substring(code.length(),lengths);
						index+=code.length();
						int indexs=node.getIndex();
						resultList.add((byte)(indexs));
					}
				}
				
			}while(index<lens);
			
			byte bbt[]=list2byte(resultList);
			FileOutputStream file=new FileOutputStream("hhh.txt");
			file.write(bbt);
			file.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String byte2str(byte b,int len){
		String result="";
		byte temp=b;
		for(int i=0;i<len;i++){
			if((temp&1)==1){
				result+=1+"";
			}else{
				result+=0+"";
			}
			temp=(byte) (temp>>1);
		}
		StringBuffer bf=new StringBuffer(result);
		return bf.reverse().toString();
	}
	
	public static byte[] list2byte(List<Byte>list){
		byte [] bb=new byte[list.size()];
		for(int i=0;i<list.size();i++){
			bb[i]=list.get(i);
		}
		return bb;
	}
}
