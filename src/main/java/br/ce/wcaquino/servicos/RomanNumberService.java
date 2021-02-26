package br.ce.wcaquino.servicos;

import br.ce.wcaquino.utils.NumberUtils;

public class RomanNumberService {
    public String getNumberInRoman(int number) {
        String numberInRoman = "";
        if (number >= 1000) {
            int value = number / 1000;
            for (int i = value; i > 0; i--) {
                numberInRoman += "M";
            }
            number = number % 1000;
        }

        if (number >= 100 && number < 1000) {
            int value = number / 100;
            numberInRoman += NumberUtils.getCentInRomanNumeral(value);
            number = number % 100;
        }

        if (number >= 10 && number < 100) {
            int value = number / 10;
            numberInRoman += NumberUtils.getTenInRomanNumeral(value);
            number = number % 10;
        }

        if (number >= 1 && number < 10) {
           numberInRoman += NumberUtils.getUnitInRomanNumeral(number);
        }
        return numberInRoman;
    }
}
