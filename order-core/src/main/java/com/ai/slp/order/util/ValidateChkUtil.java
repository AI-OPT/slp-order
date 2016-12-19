package com.ai.slp.order.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpATTRS;

public class ValidateChkUtil {

	private static final Log LOG = LogFactory.getLog(ValidateChkUtil.class);

	/**
	 * 校验校验文件
	 * 
	 * @param chkPath
	 * @param datPath
	 * @return
	 * @author zhangqiang7
	 * @throws Exception
	 * @UCUSER
	 */
	public String validateChk(String path, String localpath, String datName, String chkName, ChannelSftp sftp)
			throws Exception {
		SftpATTRS chkAttrs = null;
		SftpATTRS datAttrs = null;
		try {
			chkAttrs = sftp.lstat(path + "/" + chkName);
		} catch (Exception e) {
			LOG.error("校验文件" + chkName + "获取不到");
			//分布式系统会有一台机器找不到校验文件
			return "09";
		}
		try {
			// 校验数据文件是否存在
			datAttrs = sftp.lstat(path + "/" + datName);
		} catch (Exception e) {
			LOG.error("数据文件" + datName + "获取不到");
			return "01";
		}
		LOG.error("+++++++开始校验文件:" + datName);
		StringBuilder errCode = new StringBuilder();
		if (chkAttrs.getSize() == 0) {
			//InputStream chkIs = sftp.get(path + "/" + chkName);
			//InputStream datIs = sftp.get(path + "/" + datName);
			errCode.append("99");
		}
		// BufferedReader br = new BufferedReader(inputStreamReader);

		InputStream ins = SftpUtil.download(path, chkName, localpath, sftp);
		BufferedReader br = new BufferedReader(new InputStreamReader(ins, "utf-8"));
		String temp = null;
		temp = br.readLine();
		String[] str = {};
		while (temp != null) {
			str = temp.split("\\t");
			temp = br.readLine();
		}
		// 校验数据文件名称
		if (!datName.equals(str[0])) {
			LOG.error("+++++++++++++++校验数据文件名称有问题");
			return "99";
		}
		//校验文件大小
		try{
		if (!String.valueOf(datAttrs.getSize()).equals(str[1])) {
			errCode.append("02");
		}
		}catch(Exception e){
			errCode.append("99");
		}
		/*// 获取数据文件数据
		InputStream datInputStream = SftpUtil.download(path, datName, localpath, sftp);
		InputStreamReader datInputStreamReader = new InputStreamReader(datInputStream, "utf-8");
		BufferedReader datReader = new BufferedReader(datInputStreamReader);
		String datTemp = datReader.readLine();
		Long count = 0L;
		while (datTemp != null) {
			datTemp = datReader.readLine();
			count++;
		}

		// 校验数据文件大小
		try {
			if (!String.valueOf(datAttrs.getSize()).equals(str[1])) {
				errCode.append("02");
			}
			// 校验数据文件记录
			if (!count.toString().equals(str[2])) {
				errCode.append("03");
			}
			// 校验数据文件数据修改时间 if(!shortFormat.format(new
			
			 if(shortFormat.format(datAttrs.getMTime()).equals(str[3])){
			 errCode.append("04"); }
			 
			// 校验数据文件创建时间
			
			 Files.readAttributes(Paths.get(datPath),
			 BasicFileAttributes.class).creationTime().toMillis();
			 if(!longFormat.format(new Date(createTime)).equals(str[4])){
			 errCode.append("05"); }
			 
		} catch (Exception e) {
			errCode.append("99");
			InputStream chkIs = SftpUtil.download(path, localpath, chkName, sftp);
			SftpUtil.uploadIs(path + "/sapa/err/", chkName, chkIs, sftp);
			SftpUtil.delete(path, chkName, sftp);
		}*/
		return errCode.toString();
	}

}
