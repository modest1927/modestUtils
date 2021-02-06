package com.myutils.utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.*;
import java.util.Map.Entry;

/**
 * 处理字符串的工具类
 *
 * @since 2007.12.7
 */

public class StringUtil {
	/**
	 * 字符串转换为整数
	 * @param oriStr
	 * @param spStr
	 * @return
	 */
	public static String transStrToIn(String oriStr,String spStr){
		String rtnStr = "";
		if(oriStr==null || oriStr.equals("")){
			return null;
		}
		String[] oriStrArr = oriStr.split(spStr);
		for(int i= 0;i<oriStrArr.length;i++){
			rtnStr += "'"+oriStrArr[i]+"',";
		}
		return rtnStr.substring(0, rtnStr.length() - 1);
	}

	/**
	 * 将二进制流数据按指定编码转换成字符串
	 *
	 * @param ins
	 * @param encoding
	 * @return
	 * @throws IOException
	 */
	public static String getInputStream(InputStream ins, String encoding)
			throws IOException {
		byte[] byteBuffer = new byte[1024];
		int bytesRead = 0;
		int totBytesRead = 0;
		int totBytesWritten = 0;
		ByteArrayOutputStream outs = new ByteArrayOutputStream();
		while ((bytesRead = ins.read(byteBuffer)) != -1) {
			outs.write(byteBuffer, 0, bytesRead);
			totBytesRead += bytesRead;
			totBytesWritten += bytesRead;
		}
		return outs.toString(encoding);
	}

	/**
	 * 中文货币大写 绝对值小于1.0E15（正负1千万亿以内），小数位2位
	 *
	 * @param money
	 * @return
	 */
	public static String ChineseCapitalCurrency(BigDecimal money) {
		String units[] = { "分", "角", "圆", "拾", "佰", "仟", "万", "拾", "佰", "仟",
				"亿", "拾", "佰", "仟", "万", "拾", "佰" };
		String capNum[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String str;
		boolean bZero;
		int unitIndex;
		str = "";
		bZero = true;
		unitIndex = 0;
		if (money.intValue() == 0)
			return "零圆整";
		try {
			double dm = Math.round(money.doubleValue() * 100D);
			boolean bNegative = dm < 0.0D;
			for (dm = Math.abs(dm); dm > 0.0D;) {
				if (unitIndex == 2 && str.length() == 0)
					str = str + "整";
				if (dm % 10D > 0.0D) {
					str = capNum[(new Double(dm % 10)).intValue()]
							+ units[unitIndex] + str;
					if (unitIndex != 5 && unitIndex != 9)
						bZero = false;
				} else {
					if (unitIndex == 2) {
						if (dm > 0.0D) {
							str = units[unitIndex] + str;
							bZero = true;
						}
					} else if ((unitIndex == 6 || unitIndex == 10)
							&& dm % 1000 > 0.0D) {
						str = units[unitIndex] + str;
					}

					if (!bZero)
						str = capNum[0] + str;
					bZero = true;
				}
				dm = Math.floor(dm / 10D);
				unitIndex++;
			}

			if (bNegative)
				str = "负" + str;
		} catch (Exception e) {
			e.printStackTrace();
			return "#Error";
		}
		return str;
	}

	/* 统计文章字数 */
	private static final char[] CHS = { ',', '；', '!', '.', '!', '?', ';', '+', '，','？', '！', '/' }; // 符号数组
	private static final char[] CHN = { '\r', '\n' }; // 转义符数组
	private static final char[] SPACE = { ' ', '　' }; // 空格的数组(前半角，后全角)
	/**
	 * 根据指定条件来筛选文章的字数
	 *
	 * @param wordContent
	 *            文章内容
	 * @param compriseInterpunction
	 *            是否包含指定字符
	 * @param compriseSpace
	 *            是否包含空格
	 * @return 返回文章经过指定筛选后的长度
	 */
	public static int getWordCount(String wordContent, boolean compriseInterpunction,
			boolean compriseSpace) {
		if (wordContent == null) {
			return 0;
		} else if (wordContent.length() == 0) {
			return 0;
		} else {
			// 既要包含符号又要包含空格
			if (compriseInterpunction && compriseSpace) {
				// 清除转义符
				String regex = "[" + new String(CHN) + "]";
				wordContent = wordContent.replaceAll(regex, " ");
				return getWordCount(wordContent);
			}
			// 不包含符号包含空格
			else if (!compriseInterpunction && compriseSpace) {
				// 使用正则表达式去掉指定的符号和转义符
				String regex1 = "[" + new String(CHN) + "]";
				String regex2 = "[" + new String(CHS) + "]";
				wordContent = wordContent.replaceAll(regex1, " ");
				wordContent = wordContent.replaceAll(regex2, " ");
				return getWordCount(wordContent);
			}
			// 包含指定符号不包含空格
			else if (compriseInterpunction && !compriseSpace) {
				// 使用正则表达式去掉空格和转义符
				String regex1 = "[" + new String(CHN) + "]";
				String regex2 = "[" + new String(SPACE) + "]";
				wordContent = wordContent.replaceAll(regex1, " ");
				wordContent = wordContent.replaceAll(regex2, " ");
				return getWordCount(wordContent);
			}
			// 空格和指定符号都不包含
			else {
				// 使用正则表达式去掉空格,指定符号和转义符
				String regex1 = "[" + new String(CHN) + "]";
				String regex3 = "[" + new String(CHS) + "]";
				String regex2 = "[" + new String(SPACE) + "]";
				wordContent = wordContent.replaceAll(regex1, " ");
				wordContent = wordContent.replaceAll(regex2, " ");
				wordContent = wordContent.replaceAll(regex3, " ");
				return getWordCount(wordContent);
			}
		}
	}

	/**
	 * 返回文章中的字数
	 *
	 * @param wordCount
	 *            文章内容
	 * @return
	 */
	@SuppressWarnings("unused")
	private static int getWordCount(String wordContent) {
		int count = 0;
		if (wordContent == null) { // 判断是否为null,如果为null直接返回0
			count = 0;
		} else if (wordContent.length() == 0) { // 判断是否为空,如果为空直接返回0
			count = 0;
		} else { // 判断获取字数
			wordContent = wordContent.trim(); // 清空空格
			// 临时变量
			String s4 = "";
			String s3 = "";
			String s2 = "";
			String s1 = "";
			boolean bb = false;
			if (wordContent.length() > 0) {
				s4 = String.valueOf(wordContent
						.charAt(wordContent.length() - 1));
			}
			for (int i = 0; i < wordContent.length(); i++) {
				s3 = String.valueOf(wordContent.charAt(i));
				int num = s3.getBytes().length;
				if (s3.hashCode() == 32 || s3.getBytes().length == 2) {
					bb = true;
				}
				if (num == 2) {
					count++;
				} else {
					if (i + 1 < wordContent.length()) {
						s1 = String.valueOf(wordContent.charAt(i + 1));
						if ((s1.hashCode() == 32 && (s3.hashCode() != 32))
								|| ((s1.getBytes().length == 2) && (s3
										.hashCode() != 32))) {
							count++;
						}
					}
				}
			}
			if (!bb) {
				count++;
			} else {
				if (s4.getBytes().length == 1) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 根据条件来获取文章的字符数
	 *
	 * @param wordContent
	 *            文章内容
	 * @param compriseInterpunction
	 *            是否包含指定符号
	 * @param compriseSpace
	 *            是否包含空格
	 * @return 返回字符长度
	 */
	public static int getWordCharacter(String wordContent,
			boolean compriseInterpunction, boolean compriseSpace) {
		// 既要包含符号又要包含空格
		if (compriseInterpunction && compriseSpace) {
			// 清除转义符
			String regex = "[" + new String(CHN) + "]";
			wordContent = wordContent.replaceAll(regex, "");
			// 首部的空格不算
			wordContent = wordContent.replaceAll("^\\s+", "");
			return wordContent.length();
		}// 不包含符号包含空格
		else if (!compriseInterpunction && compriseSpace) {
			// 首部的空格不算
			wordContent = wordContent.replaceAll("^\\s+", "");
			// 使用正则表达式去掉指定的符号和转义符
			String regex1 = "[" + new String(CHN) + "]";
			String regex2 = "[" + new String(CHS) + "]";
			wordContent = wordContent.replaceAll(regex1, "");
			wordContent = wordContent.replaceAll(regex2, "");
			return wordContent.length();
		}// 包含指定符号不包含空格
		else if (compriseInterpunction && !compriseSpace) {
			// 使用正则表达式去掉空格和转义符
			String regex = "[" + new String(SPACE) + "]";
			wordContent = wordContent.replaceAll(regex, " ");
			return getNoSpaceCount(wordContent);
		}// 空格和指定符号都不包含
		else {
			// 使用正则表达式去掉指定符号
			String regex1 = "[" + new String(CHS) + "]";
			String regex2 = "[" + new String(SPACE) + "]";
			wordContent = wordContent.replaceAll(regex1, " ");
			wordContent = wordContent.replaceAll(regex2, " ");
			return getNoSpaceCount(wordContent);
		}
	}

	/**
	 * 获取文章中非空格的字符总数
	 *
	 * @param wordContent
	 *            文章内容
	 * @return
	 */
	private static int getNoSpaceCount(String wordContent) {
		int spaceCount = 0;
		if (wordContent == null) {
			spaceCount = 0;
		} else if (wordContent.length() == 0) {
			spaceCount = 0;
		} else {
			// 替换首部的
			wordContent = wordContent.replaceAll("^\\s+", "");
			wordContent = wordContent.replaceAll(" ", "");
			// 使用正则替换转义符
			String regex = "[" + new String(CHN) + "]";
			wordContent = wordContent.replaceAll(regex, "");
			spaceCount = wordContent.length();
		}
		return spaceCount;
	}


	/**
	 * 格式化数字，取整
	 * @param d
	 * @return
	 */
	public static String transNumber(Object d) {
		return d!=null?d.toString().replaceAll("\\.0", ""):"";
	}
	/**
	 * 统一文件名编码
	 * @param fileName 传入的文件名
	 * @return 重新编码后的文件名
	 */
	public static String encodingFileName(String fileName) {
        String returnFileName = "";
        try {
            returnFileName = URLEncoder.encode(fileName, "UTF-8");
            returnFileName = returnFileName.replaceAll("\\+", "%20");
            if (returnFileName.length() > 150) {
                returnFileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
                returnFileName = returnFileName.replaceAll("\\s", "%20");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return returnFileName;
    }

	/**
	 * 给传入的num补全x位，缺位用0补充
	 * @param num 需要补0的整数
	 * @param x 指定的位数
	 * @return
	 */
	public static String leadByZero(int num, int x) {
		String s = "000000000000000000000000000000000000000".substring(0, x);
		return s.substring(String.valueOf(num).length()) + num;
	}

	/**
	 * 把str中的oldStr字符串替换成newStr
	 * @param str
	 * @param oldStr
	 * @param newStr
	 * @return
	 */
    public static String strReplace(String str, String oldStr, String newStr) {
        int i = -1;
        while ( (i = str.indexOf(oldStr)) > -1) {
            str = str.substring(0, i) + newStr + str.substring(i + oldStr.length());
        }
        return str;
    }

	/**
	 * 把Str中从BStr到EStr的字符串替换成ReStr
	 * @param Str
	 * @param BStr
	 * @param EStr
	 * @param ReStr
	 * @return
	 */
    public static String strReplace(String Str, String BStr, String EStr, String ReStr) {
        String ReturnStr = "", Str1 = "", Str2 = "";
        int i, j, n;
        n = 0;
        if ( (Str.indexOf(BStr) > -1) && ( (Str.indexOf(EStr) > -1))) {
            while (Str.indexOf(BStr, n) > -1) {
                i = Str.indexOf(BStr);
                j = Str.indexOf(EStr);
                Str1 = Str.substring( (i + BStr.length()), j);
                Str2 = strReplace(ReStr, "$lichao$", Str1);
                Str1 = BStr + Str1 + EStr;
                Str = strReplace(Str, Str1, Str2);
                n = i + Str2.length() - Str1.length();
            }
        }
        ReturnStr = Str;
        return ReturnStr;
    }

	/**
	 * 获取SourceStr中使用Beg开头，End结尾的内容
	 * @param SourceStr
	 * @param Beg
	 * @param End
	 * @return
	 */
    public static String getBeginEndStr(String SourceStr , String Beg , String End) {
        String returnStr = "";
        int n = 0;
        int i = 0;
        int j = 0;
        if ( (SourceStr.indexOf(Beg) > -1) && ( (SourceStr.indexOf(End) > -1))) {
            if (SourceStr.indexOf(Beg, n) > -1) {
                i = SourceStr.indexOf(Beg);
                j = SourceStr.indexOf(End);
                returnStr = SourceStr.substring( (i + Beg.length()), j);
            }
        }
        return returnStr;
    }

    /**
     * 使用指定的分隔符（mark）分割指定字符串（strOb）
     * @param strOb
     * @param mark
     * @return 返回分割的字符串数组
     */
    public static String[] split(String strOb, String mark) {
        List<String> tmp = new ArrayList<String>();
        for (int op = 0; (op = strOb.indexOf(mark)) != -1;) {
            tmp.add(strOb.substring(0, op));
            strOb = strOb.substring(op + mark.length(), strOb.length());
        }

        String strArr[] = new String[tmp.size() + 1];
        int i;
        for (i = 0; i < tmp.size(); i++)
            strArr[i] = (String) tmp.get(i);

        strArr[i] = strOb;
        return strArr;
    }


    /**
     * 字符串数组使用指定的分隔符连接起来
     * @param strOb
     * @param mark
     * @return 返回字符串
     */
    public static String join(String[] strArr, String mark) {
    	String strOb = "";
        for (int i = 0; i < strArr.length; i++) {
			strOb += mark+strArr[i];
		}
        return strOb.length()>0?strOb.substring(mark.length()):strOb;
    }
    /**
     * 字符串list使用指定的分隔符连接起来
     * @param strOb
     * @param mark
     * @return 返回字符串
     */
    public static String join(List list, String mark) {
    	String strOb = "";
    	for (int i = 0; i < list.size(); i++) {
    		strOb += mark+(String)list.get(i);
    	}
    	return strOb.substring(mark.length());
    }
    /**
     * 对文字过多的标题进行截断（使用"\n"），并加上指定后缀
     * @param title 需分割的标题
     * @param length 指定的分割长度
     * @param postfix 后缀
     * @return 处理后的结果
     */
    public static String substring(String title, int length, String postfix) {
        if (title.length() > length) {
            String temp = title.substring(0, length - 1);
            temp = strReplace(temp,"\n","");
            return temp + postfix;
        } else {
            return title;
        }
    }

    /**
     * 查询一个字符串是否存在与字符串数组中
     * @param stringArray 字符串数组
     * @param findString    待查找的字符串
     * @param blnCase      是否区分大小写
     * @return 查找结果<true|false>
     */
    public static boolean inStringArray(String[] stringArray , String findString , boolean blnCase)
    {
        if (stringArray == null) return false;

        for(int i = 0 ; i < stringArray.length ; i++)
        {
            if (blnCase)
            {
                if (stringArray[i].equalsIgnoreCase(findString))
                    return true;

                continue;
            }

            if (stringArray[i].equals(findString))
                return true;
        }

        return false;
    }

	/**
	 * 获取图形验证码
	 * @param width
	 * @param height
	 * @param length
	 * @param useCharacter 是否包含字母
	 * @return
	 */
	public static List getVerifyImage(int width, int height, int length, boolean useCharacter){
        List list = new ArrayList();
        Random random = new Random();
        String ychar = useCharacter?"0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z":"0,1,2,3,4,5,6,7,8,9";
        String[] yc = ychar.split(",");
        String seed = "";
        for (int i = 0; i < length; i++) {
        	seed = seed + yc[random.nextInt(yc.length)];
        }
        list.add(seed);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(new Color(245, 245, 245));
        g.fillRect(0, 0, width, height);
        g.setColor(getRandColor(160, 200));

        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        g.setColor(Color.DARK_GRAY);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(seed, 8, 13);
        g.dispose();
        list.add(image);
        return list;
	}

	/**
	 * 给定范围获得随机颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }


	/**
	 * 将对象转化成字符串
	 * 为null时返回null，其它用对象自带的toString()方法转换
	 * @param obj
	 * @return
	 */
	public static String objectToString(Object obj){
		if(obj==null) {
			return null;
		} else {
			return obj.toString() ;
		}
	}
	public static String objectToString2(Object obj){
		if(obj==null) {
			return "";
		} else {
			return obj.toString() ;
		}
	}
	public static String objectToString(Object obj,String nullValue){
		if(obj==null) {
			return nullValue;
		} else {
			return obj.toString() ;
		}
	}

	public static String getRandomStr() {
		String s = DateUtil.getSystemDateTimeStr("yyyyMMddHHmmss");
		s += String.valueOf(Math.round(Math.random()*10000));
		return s;
	}

	public static String getChineseNumber(String str) {
		String[] a = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
		String s = "";
		for(int i=0; i<str.length(); i++){
			s += a[Integer.parseInt(str.substring(i, i+1))];
		}
		return s;
	}

	public static boolean testInStr(String str){
		if(str!=null) {
			return str.matches("^[A-Za-z_$\\d]+$");
		}else{
			return false ;
		}
	}
	/**
	 * 替换转义字符为指定字符
	 * @param str
	 * @param targetStr
	 */
	public static String replaceEscape(String str,String targetStr) {
		return str.replaceAll("\n",targetStr).replaceAll("\r",targetStr).replaceAll("\t",targetStr) ;
	}

	public static void main(String[] args) {
		System.out.println(testInStr("2012_aa%"));
		//System.out.println(transStrToIn("2323~233223~454456","~"));
		//System.out.println(StringUtil.getWordCharacter("is www enjoyjava net 中文", false, false));
	}
	/**
     * 类转xml方法.
     * @param data List<?>
     * @return String
     * @throws
     */
    public String object2xml(List<?> list) {
        Document document = DocumentHelper.createDocument();
        Element nodesElement = document.addElement("DATA");
        list2xml(list,nodesElement);
        return doc2String(document);
    }
    /**
     * List2XML,目前支持List<List> List<Map> List<Map<String,List>>等只有
     * List Map 组合的数据进行转换.
     * @param list
     * @param element
     * @return
     * @throws
     */
    public Element list2xml(List list, Element element){
        int i = 0;
        for (Object o : list) {
            Element nodeElement = element.addElement("LIST");
            if (o instanceof Map) {
                nodeElement.addAttribute("type", "o");
                Map m = (Map) o;
                for (Iterator iterator = m.entrySet().iterator(); iterator.hasNext();) {
                    Entry entry = (Entry) iterator.next();
                    Element keyElement = nodeElement.addElement(entry.getKey().toString());
                    if (entry.getValue() instanceof List) {
                        keyElement.addAttribute("type", "l");
                        list2xml((List) entry.getValue(),keyElement);
                    } else {
                        keyElement.addAttribute("type", "s");
                        keyElement.setText(entry.getValue().toString());
                    }
                }
            } else if (o instanceof List) {
                nodeElement.addAttribute("type", "l");
                list2xml((List)o,nodeElement);
            }
            else {
                Element keyElement = nodeElement.addElement("value");
                keyElement.addAttribute("num", String.valueOf(i));
                keyElement.setText(String.valueOf(o));
            }
            i++;
        }
        return element;
    }
    /**
     * xml转为String.
     * @param document
     * @return
     * @throws
     */
    public String doc2String(Document document) {
        String s = "";
        try {
            // 使用输出流来进行转化
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 使用UTF-8编码
            OutputFormat format = new OutputFormat("   ", true, "UTF-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            s = out.toString("UTF-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return s;
    }
    /**
     * xml转List方法.
     * @param xml
     * @return List<?>
     * @throws
     */
    public List<?> xml2List(String xml){
        try {
            List list = new ArrayList();
            Document document = DocumentHelper.parseText(xml);
            Element nodesElement = document.getRootElement();
            List nodes = nodesElement.elements();
            for (Iterator its = nodes.iterator(); its.hasNext();) {
                Element nodeElement = (Element) its.next();
                if(("l").equals(nodeElement.attributeValue("type"))){
                    List s = xml2List(nodeElement.asXML());
                    list.add(s);
                    s = null;
                }else if(("o").equals(nodeElement.attributeValue("type"))){
                    Map map = xml2Map(nodeElement.asXML());
                    list.add(map);
                    map = null;
                }else{
                    list.add(nodeElement.getText());
                }
            }
            nodes = null;
            nodesElement = null;
            document = null;
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * xml 2 map
     * @param xml
     * @return
     */
    public Map xml2Map(String xml) {
        try {
            Map map = new HashMap();
            Document document = DocumentHelper.parseText(xml);
            Element nodeElement = document.getRootElement();
            List node = nodeElement.elements();
            for (Iterator it = node.iterator(); it.hasNext();) {
                Element elm = (Element) it.next();
                if("l".equals(elm.attributeValue("type"))){
                    map.put(elm.getName(), xml2List(elm.asXML()));
                }else if("o".equals(elm.attributeValue("type"))){
                    map.put(elm.getName(), xml2Map(elm.asXML()));
                }else{
                    map.put(elm.getName(), elm.getText());
                }
                elm = null;
            }
            node = null;
            nodeElement = null;
            document = null;
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
