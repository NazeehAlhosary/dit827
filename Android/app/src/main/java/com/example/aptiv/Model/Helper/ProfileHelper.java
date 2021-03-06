package com.example.aptiv.Model.Helper;

import com.example.aptiv.Model.Class.Profile;
import com.example.aptiv.Model.Class.Zone;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class ProfileHelper {

    //threshold constants
    private final static double t_altitude = 5.0;
    private final static double t_full = 30.0;
    private final static double t_gain = 1.0;
    private final static double t_humidity = 5.0;
    private final static double t_ir = 5.0;
    private final static double t_lux = 5.0;
    private final static double t_pressure = 200.0;
    private final static double t_temp = 2.0;
    private final static double t_luminosity = 5.0;
    private final static double t_sound = 1.0;
    private final static double t_light = 5.0;

    public static boolean checkTemp(boolean increasing, Zone checked, Zone other1, Zone other2){
        if(!safetyCheck(Double.parseDouble(checked.getTemperature()))&& !increasing){
            return false;
        }
        double checkedTemp = Double.parseDouble(checked.getTemperature());
        double otherTemp1= Double.parseDouble(other1.getTemperature());
        double otherTemp2 = Double.parseDouble(other2.getTemperature());

        double diffFirst = Math.abs(checkedTemp - otherTemp1);
        double diffSecond = Math.abs(checkedTemp - otherTemp2);
        double diffOthers = Math.abs(otherTemp1 - otherTemp2);

        double averageDiff = checkedTemp - (otherTemp2 + otherTemp1 + checkedTemp)/3;
        boolean aboveAverage = averageDiff > 0;

        if(diffOthers+2 < diffFirst && diffOthers+2 < diffSecond)
        {
            return movingToThreshold(increasing,aboveAverage);
        }
        return true;
    }

    public static boolean checkAirPressure(boolean increasing,Zone checked, Zone other1, Zone other2){
        if(!safetyCheck(Double.parseDouble(checked.getPressure()))&& !increasing){
            return false;
        }
        double checkedPressure = Double.parseDouble(checked.getPressure());
        double otherPressure1= Double.parseDouble(other1.getPressure());
        double otherPressure2 = Double.parseDouble(other2.getPressure());

        double diffFirst = Math.abs(checkedPressure - otherPressure1);
        double diffSecond = Math.abs(checkedPressure - otherPressure2);
        double diffOthers = Math.abs(otherPressure1 - otherPressure2);

        double averageDiff = checkedPressure - (otherPressure1 + otherPressure2 + checkedPressure)/3;
        boolean aboveAverage = averageDiff > 0;

        if(diffOthers+5 < diffFirst && diffOthers+5 < diffSecond)
        {
            return movingToThreshold(increasing,aboveAverage);
        }
        return true;
    }

    public static boolean checkLux(boolean increasing,Zone checked, Zone other1, Zone other2){
        if(!safetyCheck(Double.parseDouble(checked.getIr())) && !increasing){
            return false;
        }
        double checkedIr = Double.parseDouble(checked.getIr());
        double otherIr1= Double.parseDouble(other1.getIr());
        double otherIr2 = Double.parseDouble(other2.getIr());

        double diffFirst = Math.abs(checkedIr - otherIr1);
        double diffSecond = Math.abs(checkedIr - otherIr2);
        double diffOthers = Math.abs(otherIr1 - otherIr2);

        double averageDiff = checkedIr - (otherIr1 + checkedIr + otherIr2)/3;
        boolean aboveAverage = averageDiff > 0;

        if(diffOthers+15 < diffFirst && diffOthers+15 < diffSecond)
        {
            return movingToThreshold(increasing,aboveAverage);
        }
        return true;
    }

    public static boolean checkSound(boolean increasing,Zone checked, Zone other1, Zone other2){
        if(!safetyCheck(Double.parseDouble(checked.getSound()))&& !increasing){
            return false;
        }
        double checkedSound = Double.parseDouble(checked.getSound());
        double otherSound1= Double.parseDouble(other1.getSound());
        double otherSound2 = Double.parseDouble(other2.getSound());

        double diffFirst = Math.abs(checkedSound - otherSound1);
        double diffSecond = Math.abs(checkedSound - otherSound2);
        double diffOthers = Math.abs(otherSound1 - otherSound2);

        double averageDiff = checkedSound - (checkedSound + otherSound1 + otherSound2)/3;
        boolean aboveAverage = averageDiff > 0;

        if(diffOthers+5 < diffFirst && diffOthers+5 < diffSecond)
        {
            return movingToThreshold(increasing,aboveAverage);
        }
        return true;
    }

    public static boolean checkHumidity(boolean increasing, Zone checked, Zone other1, Zone other2){
        if(!safetyCheck(Double.parseDouble(checked.getHumidity()))&& !increasing){
            return false;
        }
        double checkedHumidity = Double.parseDouble(checked.getHumidity());
        double otherHumidity1= Double.parseDouble(other1.getHumidity());
        double otherHumidity2 = Double.parseDouble(other2.getHumidity());

        double diffFirst = Math.abs(checkedHumidity - otherHumidity1);
        double diffSecond = Math.abs(checkedHumidity - otherHumidity2);
        double diffOthers = Math.abs(otherHumidity1 - otherHumidity2);

        double averageDiff = checkedHumidity - (otherHumidity2 + otherHumidity1 + checkedHumidity)/3;
        boolean aboveAverage = averageDiff > 0;

        if(diffOthers+5 < diffFirst && diffOthers+5 < diffSecond)
        {
            return movingToThreshold(increasing,aboveAverage);
        }
        return true;
    }

    public static boolean sampleZone(Profile target, Zone zone, Queue<Map> queue, HashMap<String, Double> sum)
    {
        if(queue.size() < 4) {
            queue.add(zone.getAll());
            return true;
        }
        else if(queue.size() >= 4) {
            int count = 0;
            queue.add(zone.getAll());
            if(sum.get("temperature") == 0 && sum.get("humidity")== 0 && sum.get("gain")== 0 && sum.get("luminosity") == 0 && sum.get("full")== 0 && sum.get("ir") == 0 && sum.get("pressure")== 0 && sum.get("sound") == 0 && sum.get("altitude") == 0 && sum.get("light") == 0 && sum.get("lux") == 0){
                count = queue.size();
            }else{
                count = queue.size() +1;
            }
            for(int i = 0; i < queue.size(); i++){
                sum = sumMaps(sum, (HashMap<String, Double>)queue.toArray()[i]);
            }

            //generate average
            HashMap<String, Double> avg = avgMap(sum,count);

            //check range of input
            HashMap<String, Boolean> hasError = checkZone(target, avg);

            if(hasError.containsValue(Boolean.FALSE)){
                queue.remove();
                return false;
            }
            else{
                queue.remove();
                return true;
            }
        }
        return true;
    }
    private static HashMap<String, Double> sumMaps(HashMap<String, Double> sum, HashMap<String, Double> element){
        sum.put("temperature", (sum.get("temperature") + element.get("temperature")));
        sum.put("humidity", (sum.get("humidity") + element.get("humidity")));
        sum.put("gain", (sum.get("gain") + element.get("gain")));
        sum.put("luminosity", (sum.get("luminosity") + element.get("luminosity")));
        sum.put("full", (sum.get("full") + element.get("full")));
        sum.put("ir", (sum.get("ir") + element.get("ir")));
        sum.put("pressure", (sum.get("pressure") + element.get("pressure")));
        sum.put("sound", (sum.get("sound") + element.get("sound")));
        sum.put("altitude", (sum.get("altitude") + element.get("altitude")));
        sum.put("light", (sum.get("light") + element.get("light")));
        sum.put("lux", (sum.get("lux") + element.get("lux")));
        //TODO maybe use line below instead?
        // element.forEach((k, v) -> sum.merge(k, v, Double::sum);
        return sum;
    }



    private static HashMap <String, Double> avgMap(HashMap<String, Double> values ,int count){
        java.util.Iterator hmIterator = values.entrySet().iterator();

        while(hmIterator.hasNext())
        {
            Map.Entry entry = (Map.Entry) hmIterator.next();
            double newVal = ((double)entry.getValue()/count);
            values.put((String)entry.getKey(), newVal);
        }
        return values;
    }

    private static HashMap<String, Boolean> checkZone(Profile profile, HashMap<String, Double> values)
    {
        HashMap<String, Boolean> checkedZone = new HashMap<>(11);
        //compares each value against the threshold
        //puts true in dict entry if within threshold, false if not

        checkedZone.put("temperature", !values.get("temperature").equals(null) && profile.getTemperature() != null ? compareThreshold(Double.parseDouble(profile.getTemperature()), values.get("temperature"), t_temp) : true);

        checkedZone.put("humidity", !values.get("humidity").equals(null) && profile.getHumidity() != null ? values.get("humidity") == null || compareThreshold(Double.parseDouble(profile.getHumidity()),values.get("humidity"), t_humidity) : true);

        checkedZone.put("gain",  true);

        checkedZone.put("luminosity", !values.get("luminosity").equals(null) && profile.getLuminosity() != null ? values.get("luminosity") == null || compareThreshold(Double.parseDouble(profile.getLuminosity()), values.get("luminosity"), t_luminosity) : true);

        checkedZone.put("full", true);

        checkedZone.put("ir", !values.get("ir").equals(null) && profile.getIr() != null ? values.get("ir") == null || compareThreshold(Double.parseDouble(profile.getIr()), values.get("ir"), t_ir): true);

        checkedZone.put("pressure",!values.get("pressure").equals(null) && profile.getPressure() != null ? values.get("pressure") == null || compareThreshold(Double.parseDouble(profile.getPressure()), values.get("pressure"), t_pressure) : true);

        checkedZone.put("sound",true);

        checkedZone.put("altitude", !values.get("altitude").equals(null) && profile.getAltitude() != null ? values.get("altitude") == null || compareThreshold(Double.parseDouble(profile.getAltitude()), values.get("altitude"), t_altitude) : true);

        checkedZone.put("light",!values.get("light").equals(null) && profile.getLight() != null ?  values.get("light") == null || compareThreshold(Double.parseDouble(profile.getLight()),values.get("light"), t_light) : true);

        checkedZone.put("lux", !values.get("ir").equals(null) && profile.getLux() != null ? values.get("lux") == null || compareThreshold(Double.parseDouble(profile.getLux()), values.get("lux"), t_lux) : true);

        return checkedZone;
    }

    public static HashMap<String, Boolean> checkZoneDifference(Zone zone1, Zone zone2)
    {
        HashMap<String, Boolean> checkedZone = new HashMap<>(11);
        //compares each value against the threshold
        //puts true in dict entry if within threshold, false if not

        checkedZone.put("temperature", zone1.getTemperature() != null && zone2.getTemperature() != null ? compareThreshold(Double.parseDouble(zone1.getTemperature()), Double.parseDouble(zone2.getTemperature()), t_temp) : true);

        checkedZone.put("humidity", zone1.getHumidity() != null && zone2.getHumidity() != null  ?  compareThreshold(Double.parseDouble(zone1.getHumidity() ),Double.parseDouble(zone2.getHumidity()), t_humidity) : true);

        checkedZone.put("gain",  true);

        checkedZone.put("luminosity", zone1.getLuminosity() != null && zone2.getLuminosity() != null  ? compareThreshold(Double.parseDouble(zone1.getLuminosity() ), Double.parseDouble(zone2.getLuminosity()), t_luminosity) : true);

        checkedZone.put("full",true);

        checkedZone.put("ir", zone1.getIr() != null && zone2.getIr() != null  ? compareThreshold(Double.parseDouble(zone1.getIr() ),Double.parseDouble(zone2.getIr()), t_ir): true);

        checkedZone.put("pressure",zone1.getPressure() != null && zone2.getPressure() != null  ?  compareThreshold(Double.parseDouble(zone1.getPressure() ), Double.parseDouble(zone2.getPressure()), t_pressure) : true);

        checkedZone.put("sound",true);

        checkedZone.put("altitude",zone1.getAltitude() != null && zone2.getAltitude() != null  ?  compareThreshold(Double.parseDouble(zone1.getAltitude() ), Double.parseDouble(zone2.getAltitude()), t_altitude) : true);

        checkedZone.put("light",zone1.getLight() != null && zone2.getLight() != null  ?  compareThreshold(Double.parseDouble(zone1.getLight() ),Double.parseDouble(zone2.getLight()), t_light) : true);

        checkedZone.put("lux", zone1.getLux() != null && zone2.getLux() != null  ? compareThreshold(Double.parseDouble(zone1.getLux()), Double.parseDouble(zone2.getLux()), t_lux) : true);

        return checkedZone;
    }

    private static boolean compareThreshold(double target, double source, double threshold){
        boolean belowThreshold = source >= target - threshold;
        boolean aboveThreshold = source <= target + threshold;
        return belowThreshold && aboveThreshold;
    }

    private static boolean movingToThreshold(boolean increasing, boolean aboveAverage){
        if((aboveAverage && !increasing) || (!aboveAverage && increasing))
        {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean safetyCheck(double checker){
        return checker > 0;
    }
}
