package com.sunastrix.astropdf.util;

import org.apache.catalina.authenticator.Constants;

public class SUtil {
    public static final double PI = 3.141592653589793D;
    public static final double RADEG = 57.29577951308232D;
    public static final double DEGRAD = 0.017453292519943295D;
    public static final int PAGE_LENGTH = 80;
    protected static final double[] y1 = new double[]{7.0D, 20.0D, 6.0D, 10.0D, 7.0D, 18.0D, 16.0D, 19.0D, 17.0D};

    public SUtil() {
    }

    public static double acosd(double X) {
        return Math.acos(X) * 57.29577951308232D;
    }

    public static double asind(double B) {
        return Math.asin(B) * 57.29577951308232D;
    }

    public static double atan2d(double X, double Y) {
        return Math.atan2(X, Y) * 57.29577951308232D;
    }

    public static double atand(double A) {
        return Math.atan(A) * 57.29577951308232D;
    }

    public static boolean between(double a, double b, double c) {
        boolean a1;
        if (a < b) {
            if ((c <= b || c >= 360.0D) && (c < 0.0D || c >= a)) {
                a1 = false;
            } else {
                a1 = true;
            }
        } else if (c > b && c < a) {
            a1 = true;
        } else {
            a1 = false;
        }

        return a1;
    }

    public static double convertDegreeIntoSignAndDegree(double degree) {
        return 0.0D;
    }

    public static double cosd(double d) {
        return Math.cos(d * 0.017453292519943295D);
    }

    public static double fract(double x) {
        long var2 = (long)x;
        return x - (double)var2;
    }

    protected static double getConstantForYears(int x) {
        return (double)x * 1.256365740740741D;
    }

    public static int[] getDHMS(double x) {
        int[] var2 = new int[4];
        int var3 = (int)x;
        var2[0] = var3;
        if (var2[0] > 6) {
            var2[0] %= 7;
        }

        double var6;
        int x1 = (int)((var6 = x - (double)var3) * 24.0D);
        var2[1] = x1;
        x1 = (int)((var6 = var6 * 24.0D - (double)x1) * 60.0D);
        var2[2] = x1;
        x1 = (int)((var6 * 60.0D - (double)x1) * 60.0D);
        var2[3] = x1;
        return var2;
    }

    public static int getEndHoraForTime(double time) {
        return 1;
    }

    public static int getEndRahukaalForTime(double time) {
        return 1;
    }

    public static int[] getHoraDivisionForDate(double date) {
        return new int[14];
    }

    public static double getNakshatraEndLongitude(int nakNumber) {
        return 0.0D;
    }

    public static int getNakshatraForLongitude(double longitude) {
        return (int)(longitude * 0.075D);
    }

    public static double getNakshatraStartLongitude(int nakNumber) {
        return 0.0D;
    }

    public static int getNumberCorrespondingToValue(String value, String[] array) throws Exception {
        for(int var2 = 0; var2 < array.length; ++var2) {
            if (value.equalsIgnoreCase(array[var2])) {
                return var2;
            }
        }

        throw new Exception();
    }

    public static String getPadaForLongitude(double longitude) {
        return "";
    }

    public static int getRasiForLongitude(double longitude) {
        return (int)(longitude / 30.0D + 1.0D);
    }

    public static double getSignEndLongitude(int signNumber) {
        return 0.0D;
    }

    public static double getSignStartLongitude(int signNumber) {
        return 0.0D;
    }

    public static int getStartHoraForTime(double time) {
        return 1;
    }

    public static int getStartRahukaalForTime(double time) {
        return 1;
    }

    public static int getSub(double d) {
        int var4 = 0;
        int var2 = (int)(d / 120.0D);
        var2 = (int)((d -= (double)((int)((double)var2 * 120.0D))) * 3.0D / 40.0D);
        d = (d - (double)var2 * 40.0D / 3.0D) * 9.0D;

        for(int var3 = 0; var3 < 9; ++var3) {
            if ((var4 = var2 + var3) >= 9) {
                var4 -= 9;
            }

            if (y1[var4] > d) {
                break;
            }

            d -= y1[var4];
        }

        return var4;
    }

    public static int getSubLordForLongitude(double longitude) {
        return getSub(longitude);
    }

    public static int getSubSub(double d) {
        int var4 = 0;
        int var2 = (int)(d / 120.0D);
        var2 = (int)((d -= (double)((int)((double)var2 * 120.0D))) * 3.0D / 40.0D);
        d = (d - (double)var2 * 40.0D / 3.0D) * 9.0D;

        int var3;
        for(var3 = 0; var3 < 9; ++var3) {
            if ((var4 = var2 + var3) >= 9) {
                var4 -= 9;
            }

            if (y1[var4] > d) {
                break;
            }

            d -= y1[var4];
        }

        var3 = var4;
        d = 120.0D / y1[var4] * d;

        for(var2 = 0; var2 < 9; ++var2) {
            if ((var4 = var3 + var2) >= 9) {
                var4 -= 9;
            }

            if (y1[var4] > d) {
                break;
            }

            d -= y1[var4];
        }

        return var4;
    }

    public static int getSubSubLordForLongitude(double longitude) {
        return getSubSub(longitude);
    }

    public static boolean isKhanaBhavaKendra(int khanaNo) {
        boolean var1 = false;
        if (khanaNo == 1 || khanaNo == 4 || khanaNo == 7 || khanaNo == 10) {
            var1 = true;
        }

        return var1;
    }



    public static double makeDHMStoDouble(int hour, int min, int sec) {
        return (double)hour + (double)min / 60.0D + (double)sec / 3600.0D;
    }

    public static String makelength(String x, int y) {
        y -= x.length();

        for(int var2 = 0; var2 < y; ++var2) {
            x = "0" + x;
        }

        return x;
    }

    public static String makespace(int x, int y) {
        String x1 = (new Integer(x)).toString();
        y -= x1.length();

        for(int var2 = 0; var2 < y; ++var2) {
            x1 = " " + x1;
        }

        return x1;
    }

    public static String makespace(String x, int y) {
        x = x;
        y -= x.length();

        for(int var2 = 0; var2 < y; ++var2) {
            x = " " + x;
        }

        return x;
    }

    public static double makeDHMStoDouble(int day, int hour, int min, int sec) {
        return (double)day + (double)hour / 24.0D + (double)min / 1440.0D + (double)sec / 86400.0D;
    }

    public static String makeStringMiddle(String name) {
        int var1 = (80 - name.length()) / 2 + name.length();
        return makespace(name, var1);
    }

    public static StringBuffer makeStringSpaced(String name) {
        StringBuffer var1 = new StringBuffer();

        for(int var2 = 0; var2 < name.length(); ++var2) {
            var1.append(name.charAt(var2) + " ");
        }

        return var1;
    }

    public static double sind(double x) {
        return Math.sin(x * 0.017453292519943295D);
    }

    public static double tand(double z) {
        return Math.tan(z * 0.017453292519943295D);
    }

    public static double kpayan(double dd, double mm, double yy) {
        return (yy + (mm * 30.0D + dd) / 365.0D - 297.3204723D) * 50.2388475D / 3600.0D;
    }

    public static double kpayannew(double dd, double mm, double yy) {
        double var8 = 22.0D + (1335.0D + (yy - 1900.0D) * 50.2388475D) / 3600.0D + (yy - 1900.0D) * (yy - 1900.0D) * 1.11E-4D / 3600.0D;
        double var12 = ((mm - 1.0D) * 30.0D + (dd - 1.0D)) / 3600.0D / 365.0D * 50.2410675D;
        return var8 + var12;
    }

    public static double kpayankhu(double dd, double mm, double yy) {
        double var8 = (yy - 291.0D) * 365.25D + (mm * 30.0D + dd - 114.0D);
        double var6;
        return (var6 = var8 * 0.1375464681724846D) / 3600.0D;
    }

    public static double kpayannew2(double dd, double mm, double yy) {
        double var8 = 22.0D + (1335.0D + (yy - 1900.0D) * 50.2388475D) / 3600.0D + (yy - 1900.0D) * (yy - 1900.0D) * 1.11E-4D / 3600.0D;
        double var12 = ((mm - 1.0D) * 30.0D + (dd - 1.0D)) / 3600.0D / 365.0D * 50.2410675D;
        return var8 + var12;
    }

    public static boolean cl(String str) {
        char[] var1 = new char[]{'0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0'};
        return str.equals(new String(var1));
    }

    public static boolean isExalted(int planetNumber, int sign) {
        boolean var2 = false;
        switch(planetNumber) {
        case 1:
            if (sign == 1) {
                var2 = true;
            }
            break;
        case 2:
            if (sign == 2) {
                var2 = true;
            }
            break;
        case 3:
            if (sign == 10) {
                var2 = true;
            }
            break;
        case 4:
            if (sign == 6) {
                var2 = true;
            }
            break;
        case 5:
            if (sign == 4) {
                var2 = true;
            }
            break;
        case 6:
            if (sign == 12) {
                var2 = true;
            }
            break;
        case 7:
            if (sign == 7) {
                var2 = true;
            }
        }

        return var2;
    }

    public static boolean isDeblited(int planetNumber, int sign) {
        boolean var2 = false;
        switch(planetNumber) {
        case 1:
            if (sign == 7) {
                var2 = true;
            }
            break;
        case 2:
            if (sign == 8) {
                var2 = true;
            }
            break;
        case 3:
            if (sign == 4) {
                var2 = true;
            }
            break;
        case 4:
            if (sign == 12) {
                var2 = true;
            }
            break;
        case 5:
            if (sign == 10) {
                var2 = true;
            }
            break;
        case 6:
            if (sign == 6) {
                var2 = true;
            }
            break;
        case 7:
            if (sign == 1) {
                var2 = true;
            }
        }

        return var2;
    }

    public static boolean isInOwnSign(int planetNumber, int sign) {
        boolean var2 = false;
        switch(planetNumber) {
        case 1:
            if (sign != 5) {
                return var2;
            }
            break;
        case 2:
            if (sign != 4) {
                return var2;
            }
            break;
        case 3:
            if (sign == 1 || sign == 8) {
                var2 = true;
            }

            return var2;
        case 4:
            if (sign != 3) {
                return var2;
            }
            break;
        case 5:
            if (sign == 9 || sign == 12) {
                var2 = true;
            }

            return var2;
        case 6:
            if (sign == 2 || sign == 7) {
                var2 = true;
            }

            return var2;
        case 7:
            if (sign != 10 && sign != 11) {
                return var2;
            }
            break;
        default:
            return var2;
        }

        var2 = true;
        return var2;
    }

    public static double sub_mod(double value) {
        if (value < 0.0D) {
            value += 360.0D;
        }

        return value;
    }
}
