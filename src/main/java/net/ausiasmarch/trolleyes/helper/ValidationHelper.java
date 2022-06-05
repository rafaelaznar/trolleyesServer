package net.ausiasmarch.trolleyes.helper;

public class ValidationHelper {

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateDNI(String itDNI) {
        String strDNI = itDNI.trim().replaceAll(" ", "");
        if (strDNI.length() == 9) {
            if (isNumeric(strDNI.substring(0, 8))) {
                int intPartDNI = Integer.parseInt(strDNI.substring(0, 8));
                char cLetraDNI = strDNI.charAt(8);
                int valNumDni = intPartDNI % 23;
                if ("TRWAGMYFPDXBNJZSQVHLCKE".charAt(valNumDni) != cLetraDNI) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean validateStringLength(String strNombre, int minlength, int maxlength) {
        if (strNombre.length() >= minlength && strNombre.length() <= maxlength) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateNombre(String strNombre) {
        if (validateStringLength(strNombre, 2, 50)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateDescripcion(String strDesc) {
        if (validateStringLength(strDesc, 5, 255)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validateEmail(String email) {
        if (!validateStringLength(email, 3, 255)) {
            return false;
        }
        String ePattern = "^.+@.+\\..+$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static boolean validateLogin(String strLogin) {
        if (!validateStringLength(strLogin, 6, 20)) {
            return false;
        }
        String ePattern = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){4,18}[a-zA-Z0-9]$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(strLogin);
        return m.matches();
    }

    public static boolean validateIntRange(int iNumber, int iMin, int iMax) {
        if (iNumber >= iMin && iNumber <= iMax) {
            return true;
        } else {
            return false;
        }
    }

}
