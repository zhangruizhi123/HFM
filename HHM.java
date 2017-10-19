package com.cjeg.hfm;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class HHM {
	
	static int size=0;
	public static final int MAX = 256;
	public static void main(String[] args) {
		
		int index[]=new int[MAX];
		try {
			FileInputStream in=new FileInputStream("abc.txt");
			ByteArrayOutputStream out=new ByteArrayOutputStream();
			int len=0;
			byte bb[]=new byte[1024];
			while((len=in.read(bb))>0){
				out.write(bb,0,len);
				for(int i=0;i<len;i++){
					int t= bb[i]+128;
					index[t]++;
				}
			}
			
			List<HFNode> node=new LinkedList<HFNode>();
			//过滤掉值为0的空节点
			for(int i=0;i<index.length;i++){
				if(index[i]!=0){
					HFNode no=new HFNode();
					no.setIndex(i);
					no.setSize(index[i]);
					no.setChild(true);
					node.add(no);
				}
			}
			Collections.sort(node);
			//获取前两个节点
			do{
				HFNode left=node.remove(0);
				HFNode right=node.remove(0);
				HFNode nHFNode=new HFNode();
				nHFNode.setLeft(left);
				nHFNode.setRight(right);
				nHFNode.setIndex(left.getIndex()+right.getIndex());
				node.add(nHFNode);
			}while(node.size()>1);
			//获取节点与字符表的映射
			List<HFNode> hNode=new ArrayList<HFNode>();
			Map<String,HFNode> map=new HashMap<String,HFNode>();
			forNode(hNode,node.get(0),"");
			for(HFNode nodes:hNode){
				map.put(nodes.getIndex()+"", nodes);
			}
			//开始压缩
			//获取所有的字节
			byte []dde=out.toByteArray();
			StringBuffer buffer=new StringBuffer();
			for(byte b:dde){
				HFNode temp=map.get((b+128)+"");
				buffer.append(temp.getCode());
			}
			
			List<Byte> bt=new ArrayList<Byte>();
			String bbe=buffer.toString();
			for(int i=0;i<bbe.length()/8;i++){
				String code=bbe.substring(i*8,(i+1)*8);
				bt.add(str2byte(code));
			}
			int length=bbe.length()/8;
			if(bbe.length()%8!=0){
				String code=bbe.substring(length*8,bbe.length());
				bt.add(str2byte(code));
			}
			byte [] be=list2byte(bt);
			//开始写入数据
			DataOutputStream dataout=new DataOutputStream(new FileOutputStream("dde.zips"));
			dataout.writeShort(hNode.size());//表的大小
			dataout.writeInt(be.length);//数据大小
			System.out.println(hNode.size());
			//写入表
			for(int i=0;i<hNode.size();i++){
				HFNode tempNode=hNode.get(i);
				byte ind=(byte) (tempNode.getIndex()-128);
				byte code=str2byte(tempNode.getCode());
				dataout.writeByte(ind);
				dataout.writeByte(code);
				dataout.writeByte(tempNode.getCode().length());
			}
			//写入压缩后的数据
			dataout.write(be);
			dataout.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void forNode(List<HFNode> hNode,HFNode node,String parent){
		if(node.isChild()){
			node.setCode(parent);
			hNode.add(node);
		}
		if(node.getLeft()!=null){
			forNode(hNode,node.getLeft(),parent+"0");
		}
		if(node.getRight()!=null){
			forNode(hNode,node.getRight(),parent+"1");
		}
	}
	
	public static byte str2byte(String str){
		byte  b=0;
		for(int i=0;i<str.length();i++){
			String d=str.substring(i, i+1);
			if(d.equals("1")){
				b=(byte) (b|1);
			}else{
				b=(byte) (b& 254);
			}
			if(i<(str.length()-1)){
				b=(byte) (b<<1);
			}
		}
		return b;
	}
	public static byte[] list2byte(List<Byte>list){
		byte [] bb=new byte[list.size()];
		for(int i=0;i<list.size();i++){
			bb[i]=list.get(i);
		}
		return bb;
	}
}
