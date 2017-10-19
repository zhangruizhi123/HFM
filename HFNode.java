package com.cjeg.hfm;

public class HFNode implements Comparable<HFNode> {
	//代表哪个节点
	private int index;
	//节点的个数
	private int size;
	private HFNode left;
	private HFNode right;
	//建立后的编码
	private String code;
	
	private boolean isChild=false;
	
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public HFNode getLeft() {
		return left;
	}
	public void setLeft(HFNode left) {
		this.left = left;
	}
	public HFNode getRight() {
		return right;
	}
	public void setRight(HFNode right) {
		this.right = right;
	}
	public boolean isChild() {
		return isChild;
	}
	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	@Override
	public String toString() {
		return "HFNode [index=" + index + ", size=" + size + ", left=" + left
				+ ", right=" + right + ", code=" + code + ", isChild="
				+ isChild + "]";
	}
	@Override
	public int compareTo(HFNode node) {
		return this.size-node.size;
	}
}
