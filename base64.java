import java.math.*;

public class base64
{
    public static final char[] baseTable = {'A','B','C','D','E','F','G','H','I','J',
                                            'K','L','M','N','O','P','Q','R','S','T',
                                            'U','V','W','X','Y','Z','a','b','c','d',
                                            'e','f','g','h','i','j','k','l','m','n',
                                            'o','p','q','r','s','t','u','v','w','x',
                                            'y','z','0','1','2','3','4','5','6','7',
                                            '8','9','+','/'};

    public String encode (String text)
    {
        String encodedText = "";
        String fullBinary = "";
        char[] chars = text.toCharArray();

        for(int i = 0; i < chars.length; i++)
        {
            String binary = Integer.toBinaryString(chars[i]);

            for(int x = binary.length(); x < 8; x++)
                fullBinary += "0";

            fullBinary += binary;
        }

        String[] binarySplit = fullBinary.split("(?<=\\G.{6})");
        int padding = 0;

        for(int i = binarySplit[binarySplit.length - 1].length(); i < 6; i++)
        {
            binarySplit[binarySplit.length - 1] += "0";
            padding ++;
        }

        for(int i = 0; i < binarySplit.length; i++)
            encodedText += baseTable[Integer.parseInt(binarySplit[i], 2)];

        int padNum;

        switch(padding)
        {
            case 1: padNum = 4;
                return encodedText + "==";

            case 2: padNum = 2;
                return encodedText + "=";

            default: padNum = 0;
                return encodedText;
        }
    }

    public String decode (String text)
    {
        String decodedText = "";
        String fullBinary = "";
        char[] chars = text.toCharArray();

        for(int i = 0; i < chars.length; i++)
        {
            if(chars[i] == '=')
                fullBinary += "00";

            else
                for(int x = 0; x < baseTable.length; x++)
                    if(baseTable[x] == chars[i])
                    {
                        String binary = Integer.toBinaryString(x);

                        for(int y = binary.length(); y < 6; y++)
                            fullBinary += "0";

                        fullBinary += binary;
                        x = baseTable.length;
                    }
        }

        String[] binarySplit = fullBinary.split("(?<=\\G.{8})");

        for(int i = 0; i < binarySplit.length; i++)
            decodedText += ((char)Integer.parseInt(binarySplit[i], 2));

        return decodedText;
    }
}
