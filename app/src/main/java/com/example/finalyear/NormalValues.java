package com.example.finalyear;

public class NormalValues {
    String age;
    String gender;
    String height;
    String weight;

    NormalValues(String Age, String Height, String Weight, String Gender){
        age = Age;
        height = Height;
        weight = Weight;
        gender = Gender;
    }


    public String calculateHeartRate(){
        String heartRate;

        if (gender.equals("Male")){
            if(Integer.parseInt(age)>=0 && Integer.parseInt(age)<=25){
                heartRate = "70 - 73";
            }

            else if(Integer.parseInt(age)>=26 && Integer.parseInt(age)<=35){
                heartRate = "71 - 74";
            }

            else if(Integer.parseInt(age)>=36 && Integer.parseInt(age)<=45){
                heartRate = "71 - 75";
            }

            else if(Integer.parseInt(age)>=46 && Integer.parseInt(age)<=55){
                heartRate = "72 - 76";
            }

            else if(Integer.parseInt(age)>=56 && Integer.parseInt(age)<=64){
                heartRate = "72 - 75";
            }

            else{
                heartRate = "70 - 73";
            }

            //double heartRateValue = 203.7 / (1 + Math.pow(Math.E,(0.033 * ((double)age - 104.3))));
            //heartRate = heartRateValue-5+"-"+heartRateValue+5;
        }

        else{

            if(Integer.parseInt(age)>=0 && Integer.parseInt(age)<=25){
                heartRate = "74 - 78";
            }

            else if(Integer.parseInt(age)>=26 && Integer.parseInt(age)<=35){
                heartRate = "73 - 76";
            }

            else if(Integer.parseInt(age)>=36 && Integer.parseInt(age)<=45){
                heartRate = "74 - 78";
            }

            else if(Integer.parseInt(age)>=46 && Integer.parseInt(age)<=55){
                heartRate = "74 - 77";
            }

            else if(Integer.parseInt(age)>=56 && Integer.parseInt(age)<=64){
                heartRate = "74 - 77";
            }

            else{
                heartRate = "73 - 76";
            }
            //double heartRateValue = 190.2 / (1 + Math.pow(Math.E , (0.0453 * ((double)age - 107.5))));
            //heartRate = heartRateValue-5+"-"+heartRateValue+5;
        }

        return heartRate;
    }

    public String calculateBloodPressure(){
        String bloodPressure;

        if (Integer.parseInt(age) >= 15 && Integer.parseInt(age) <= 18){
            bloodPressure = "105/73 - 120/81 mmHg";
        }
        else if (Integer.parseInt(age) >= 19 && Integer.parseInt(age) <= 24){
            bloodPressure = "108/75 - 132/83 mmHg";
        }
        else if (Integer.parseInt(age) >= 25 && Integer.parseInt(age) <= 29){
            bloodPressure = "109/76 - 133/84 mmHg";
        }
        else if (Integer.parseInt(age) >= 30 && Integer.parseInt(age) <= 35){
            bloodPressure = "110/77 - 134/85 mmHg";
        }
        else if (Integer.parseInt(age) >= 36 && Integer.parseInt(age) <= 39){
            bloodPressure = "111/78 - 135/86 mmHg";
        }
        else if (Integer.parseInt(age) >= 40 && Integer.parseInt(age) <= 45){
            bloodPressure = "112/79 - 137/87 mmHg";
        }
        else if (Integer.parseInt(age) >= 46 && Integer.parseInt(age) <= 50){
            bloodPressure = "115/80 - 139/88 mmHg";
        }
        else if (Integer.parseInt(age) >= 51 && Integer.parseInt(age) <= 55){
            bloodPressure = "116/81 - 142/89 mmHg";
        }
        else{
            bloodPressure = "118/82 - 144/90 mmHg";
        }

        return bloodPressure;
    }

    public String calculateRespirationRate(){
        String respirationRate;
        if(Integer.parseInt(age)>7){
            respirationRate = "12-20 breaths per minute";
        }
        else{
            respirationRate = "18-30 breaths per minute";
        }

        return respirationRate;
    }

    public String calculateBloodOxygen(){
        return "95% - 100%";
    }

}

/*        else{
            if (age >= 15 && age <= 18){
                bloodPressure = "117/77 mmHg";
            }
            if (age >= 19 && age <= 24){
                bloodPressure = "120/79 mmHg";
            }
            if (age >= 25 && age <= 29){
                bloodPressure = "120/80 mmHg";
            }
            if (age >= 30 && age <= 35){
                bloodPressure = "122/81 mmHg";
            }
            if (age >= 36 && age <= 39){
                bloodPressure = "123/82 mmHg";
            }
            if (age >= 40 && age <= 45){
                bloodPressure = "124/83 mmHg";
            }
            if (age >= 46 && age <= 50){
                bloodPressure = "126/84 mmHg";
            }
            if (age >= 51 && age <= 55){
                bloodPressure = "129/85 mmHg";
            }
            if (age >= 56 && age <= 60){
                bloodPressure = "130/86 mmHg";
            }
            else{
                bloodPressure = "134/87 mmHg";
            }
        }*/
