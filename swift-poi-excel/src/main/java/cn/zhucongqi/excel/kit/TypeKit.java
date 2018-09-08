/*
 * Copyright 2018 Jobsz (zcq@zhucongqi.cn)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
*/
package cn.zhucongqi.excel.kit;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;

/**
 * 类型转换工具类
 *
 * @author Jobsz
 */
public class TypeKit {

	private static List<SimpleDateFormat> DATE_FORMAT_LIST = new ArrayList<SimpleDateFormat>(4);

    static {
        DATE_FORMAT_LIST.add(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"));
        DATE_FORMAT_LIST.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static Object convert(String value, Class<?> type, String format, boolean us) {
        if (isNotEmpty(value)) {
            if (String.class.equals(type)) {
                return value;
            }
            if (Integer.class.equals(type) || int.class.equals(type)) {
                return Integer.parseInt(value);
            }
            if (Double.class.equals(type) || double.class.equals(type)) {
                return Double.parseDouble(value);
            }
            if (Float.class.equals(type) || float.class.equals(type)) {
				return Float.parseFloat(value);
			}
            if (Boolean.class.equals(type) || boolean.class.equals(type)) {
                String valueLower = value.toLowerCase();
                if (valueLower.equals("true") || valueLower.equals("false")) {
                    return Boolean.parseBoolean(value.toLowerCase());
                }
                Integer integer = Integer.parseInt(value);
                if (integer == 0) {
                    return false;
                } else {
                    return true;
                }
            }
            if (Long.class.equals(type) || long.class.equals(type)) {
                return Long.parseLong(value);
            }
            if (BigInteger.class.equals(type)) {
				return (new BigInteger(value));
			}
            if (byte[].class.equals(type)) {
				return value.getBytes();
			}
            if (Short.class.equals(type)) {
				return Short.parseShort(value);
			}
            if (Byte.class.equals(type)) {
				return Byte.parseByte(value);
			}
            if (Date.class.equals(type) 
            		|| java.sql.Date.class.equals(type)
            		|| java.sql.Time.class.equals(type)
            		|| java.sql.Timestamp.class.equals(type)) {
            	Date date;
                if (value.contains("-") || value.contains("/") || value.contains(":")) {
                    date = getSimpleDateFormatDate(value, format);
                } else {
                    Double d = Double.parseDouble(value);
                    date = HSSFDateUtil.getJavaDate(d, us);
                }
                if (java.sql.Date.class.equals(type)) {
                	return new java.sql.Date(date.getTime());
				}
                if (java.sql.Time.class.equals(type)) {
					return new java.sql.Time(date.getTime());
				}
                if (java.sql.Timestamp.class.equals(type)) {
					return new java.sql.Timestamp(date.getTime());
				}
                return date;
            }
            if (BigDecimal.class.equals(type)) {
                return new BigDecimal(value);
            }
        }
        return null;
    }
    
    public static Object convert(String value, Field field, String format, boolean us) {
    	return TypeKit.convert(value, field.getType(), format, us);
    }

    public static String getDefaultDateString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);

    }

    public static Date getSimpleDateFormatDate(String value, String format) {
        if (isNotEmpty(value)) {
            Date date = null;
            if (isNotEmpty(format)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                try {
                    date = simpleDateFormat.parse(value);
                    return date;
                } catch (ParseException e) {
                }
            }
            for (SimpleDateFormat dateFormat : DATE_FORMAT_LIST) {
                try {
                    date = dateFormat.parse(value);
                } catch (ParseException e) {
                }
                if (date != null) {
                    break;
                }
            }

            return date;

        }
        return null;

    }

    private static Boolean isNotEmpty(String value) {
        if (value == null) {
            return false;
        }
        if (value.trim().equals("")) {
            return false;
        }
        return true;

    }

    public static String formatFloat(String value) {
        if (value.contains(".")) {
            if (isNumeric(value)) {
                try {
                    BigDecimal decimal = new BigDecimal(value);
                    BigDecimal setScale = decimal.setScale(10, BigDecimal.ROUND_HALF_DOWN).stripTrailingZeros();
                    return setScale.toPlainString();
                } catch (Exception e) {
                }
            }
        }
        return value;
    }

    public static final Pattern pattern = Pattern.compile("[\\+\\-]?[\\d]+([\\.][\\d]*)?([Ee][+-]?[\\d]+)?$");

    private static boolean isNumeric(String str) {
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
