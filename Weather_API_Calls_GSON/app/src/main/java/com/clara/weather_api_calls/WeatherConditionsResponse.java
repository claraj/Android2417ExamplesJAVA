package com.clara.weather_api_calls;

/**
 *  Mirrors the structure of the JSON object returned for a condtions request
 *  GSON uses this to turn the JSON into a WeatherCondtions object
 *
 *  Used the excellent http://pojo.sodhanalibrary.com/Convert to auto-generate the classes needed
 *
 *  The classes don't have to have all of the attributes present in the JSON
 *  And, if the JSON may or may not have an attribute, can add it to the class and it will
 *  be ignored or be null if not present, for example, Error is not an attribute if
 *  WU returns valid data.
 *
 */


public class WeatherConditionsResponse {


	private Response response;

	private Current_observation current_observation;

	public Response getResponse ()
	{
		return response;
	}

	public void setResponse (Response response)
	{
		this.response = response;
	}

	public Current_observation getCurrent_observation ()
	{
		return current_observation;
	}

	public void setCurrent_observation (Current_observation current_observation)
	{
		this.current_observation = current_observation;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [response = "+response+", current_observation = "+current_observation+"]";
	}

}

class Response
{
	//private Features features;

	private String termsofService;

	private String version;

	private Error error;      //For responses with Error. Not present in success response.

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

	public String getTermsofService ()
	{
		return termsofService;
	}

	public void setTermsofService (String termsofService)
	{
		this.termsofService = termsofService;
	}

	public String getVersion ()
	{
		return version;
	}

	public void setVersion (String version)
	{
		this.version = version;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [termsofService  " + termsofService+", version = "+version+" error = " + error + "]";
	}
}

class Current_observation
{
	private String wind_gust_mph;

	private String precip_1hr_metric;

	private String precip_today_metric;

	private String pressure_trend;

	private String forecast_url;

	private String history_url;

	//private String estimated;   //This is now an Object so can either ignore if not needed, or generate a class if we need estimated data

	private String weather;

	private String windchill_string;

	private String station_id;

	private String UV;

	private String observation_epoch;

	private String wind_gust_kph;

	private String precip_1hr_in;

	private String observation_time;

	private String feelslike_string;

	private String temp_f;

	private String local_tz_long;

	private String relative_humidity;

	private String temp_c;

	//private Image image;    //Don't care about, would need an Image class if we did

	private String solarradiation;

	private String visibility_mi;

	//private Observation_location observation_location;     //Same

	private String wind_mph;

	private String heat_index_c;

	private String precip_today_string;

	private String observation_time_rfc822;

	private String feelslike_f;

	private String heat_index_f;

	private String feelslike_c;

	private String heat_index_string;

	private String ob_url;

	private String dewpoint_string;

	private String local_tz_offset;

	private String wind_kph;

	private String windchill_f;

	private String windchill_c;

	private String wind_degrees;

	private String pressure_in;

	private String dewpoint_c;

	private String pressure_mb;

	private String icon;

	private String local_time_rfc822;

	private String precip_1hr_string;

	private String icon_url;

	private String wind_dir;

	private String dewpoint_f;

	private String nowcast;

	//private Display_location display_location;      //And same

	private String visibility_km;

	private String temperature_string;

	private String local_tz_short;

	private String local_epoch;

	private String wind_string;

	private String precip_today_in;

	public String getWind_gust_mph ()
	{
		return wind_gust_mph;
	}

	public void setWind_gust_mph (String wind_gust_mph)
	{
		this.wind_gust_mph = wind_gust_mph;
	}

	public String getPrecip_1hr_metric ()
	{
		return precip_1hr_metric;
	}

	public void setPrecip_1hr_metric (String precip_1hr_metric)
	{
		this.precip_1hr_metric = precip_1hr_metric;
	}

	public String getPrecip_today_metric ()
	{
		return precip_today_metric;
	}

	public void setPrecip_today_metric (String precip_today_metric)
	{
		this.precip_today_metric = precip_today_metric;
	}

	public String getPressure_trend ()
	{
		return pressure_trend;
	}

	public void setPressure_trend (String pressure_trend)
	{
		this.pressure_trend = pressure_trend;
	}

	public String getForecast_url ()
	{
		return forecast_url;
	}

	public void setForecast_url (String forecast_url)
	{
		this.forecast_url = forecast_url;
	}

	public String getHistory_url ()
	{
		return history_url;
	}

	public void setHistory_url (String history_url)
	{
		this.history_url = history_url;
	}

//	public String getEstimated ()
//	{
//		return estimated;
//	}
//
//	public void setEstimated (String estimated)
//	{
//		this.estimated = estimated;
//	}

	public String getWeather ()
	{
		return weather;
	}

	public void setWeather (String weather)
	{
		this.weather = weather;
	}

	public String getWindchill_string ()
	{
		return windchill_string;
	}

	public void setWindchill_string (String windchill_string)
	{
		this.windchill_string = windchill_string;
	}

	public String getStation_id ()
	{
		return station_id;
	}

	public void setStation_id (String station_id)
	{
		this.station_id = station_id;
	}

	public String getUV ()
	{
		return UV;
	}

	public void setUV (String UV)
	{
		this.UV = UV;
	}

	public String getObservation_epoch ()
	{
		return observation_epoch;
	}

	public void setObservation_epoch (String observation_epoch)
	{
		this.observation_epoch = observation_epoch;
	}

	public String getWind_gust_kph ()
	{
		return wind_gust_kph;
	}

	public void setWind_gust_kph (String wind_gust_kph)
	{
		this.wind_gust_kph = wind_gust_kph;
	}

	public String getPrecip_1hr_in ()
	{
		return precip_1hr_in;
	}

	public void setPrecip_1hr_in (String precip_1hr_in)
	{
		this.precip_1hr_in = precip_1hr_in;
	}

	public String getObservation_time ()
	{
		return observation_time;
	}

	public void setObservation_time (String observation_time)
	{
		this.observation_time = observation_time;
	}

	public String getFeelslike_string ()
	{
		return feelslike_string;
	}

	public void setFeelslike_string (String feelslike_string)
	{
		this.feelslike_string = feelslike_string;
	}

	public String getTemp_f ()
	{
		return temp_f;
	}

	public void setTemp_f (String temp_f)
	{
		this.temp_f = temp_f;
	}

	public String getLocal_tz_long ()
	{
		return local_tz_long;
	}

	public void setLocal_tz_long (String local_tz_long)
	{
		this.local_tz_long = local_tz_long;
	}

	public String getRelative_humidity ()
	{
		return relative_humidity;
	}

	public void setRelative_humidity (String relative_humidity)
	{
		this.relative_humidity = relative_humidity;
	}

	public String getTemp_c ()
	{
		return temp_c;
	}

	public void setTemp_c (String temp_c)
	{
		this.temp_c = temp_c;
	}

//	public Image getImage ()
//	{
//		return image;
//	}
//
//	public void setImage (Image image)
//	{
//		this.image = image;
//	}

	public String getSolarradiation ()
	{
		return solarradiation;
	}

	public void setSolarradiation (String solarradiation)
	{
		this.solarradiation = solarradiation;
	}

	public String getVisibility_mi ()
	{
		return visibility_mi;
	}

	public void setVisibility_mi (String visibility_mi)
	{
		this.visibility_mi = visibility_mi;
	}

//	public Observation_location getObservation_location ()
//	{
//		return observation_location;
//	}
//
//	public void setObservation_location (Observation_location observation_location)
//	{
//		this.observation_location = observation_location;
//	}

	public String getWind_mph ()
	{
		return wind_mph;
	}

	public void setWind_mph (String wind_mph)
	{
		this.wind_mph = wind_mph;
	}

	public String getHeat_index_c ()
	{
		return heat_index_c;
	}

	public void setHeat_index_c (String heat_index_c)
	{
		this.heat_index_c = heat_index_c;
	}

	public String getPrecip_today_string ()
	{
		return precip_today_string;
	}

	public void setPrecip_today_string (String precip_today_string)
	{
		this.precip_today_string = precip_today_string;
	}

	public String getObservation_time_rfc822 ()
	{
		return observation_time_rfc822;
	}

	public void setObservation_time_rfc822 (String observation_time_rfc822)
	{
		this.observation_time_rfc822 = observation_time_rfc822;
	}

	public String getFeelslike_f ()
	{
		return feelslike_f;
	}

	public void setFeelslike_f (String feelslike_f)
	{
		this.feelslike_f = feelslike_f;
	}

	public String getHeat_index_f ()
	{
		return heat_index_f;
	}

	public void setHeat_index_f (String heat_index_f)
	{
		this.heat_index_f = heat_index_f;
	}

	public String getFeelslike_c ()
	{
		return feelslike_c;
	}

	public void setFeelslike_c (String feelslike_c)
	{
		this.feelslike_c = feelslike_c;
	}

	public String getHeat_index_string ()
	{
		return heat_index_string;
	}

	public void setHeat_index_string (String heat_index_string)
	{
		this.heat_index_string = heat_index_string;
	}

	public String getOb_url ()
	{
		return ob_url;
	}

	public void setOb_url (String ob_url)
	{
		this.ob_url = ob_url;
	}

	public String getDewpoint_string ()
	{
		return dewpoint_string;
	}

	public void setDewpoint_string (String dewpoint_string)
	{
		this.dewpoint_string = dewpoint_string;
	}

	public String getLocal_tz_offset ()
	{
		return local_tz_offset;
	}

	public void setLocal_tz_offset (String local_tz_offset)
	{
		this.local_tz_offset = local_tz_offset;
	}

	public String getWind_kph ()
	{
		return wind_kph;
	}

	public void setWind_kph (String wind_kph)
	{
		this.wind_kph = wind_kph;
	}

	public String getWindchill_f ()
	{
		return windchill_f;
	}

	public void setWindchill_f (String windchill_f)
	{
		this.windchill_f = windchill_f;
	}

	public String getWindchill_c ()
	{
		return windchill_c;
	}

	public void setWindchill_c (String windchill_c)
	{
		this.windchill_c = windchill_c;
	}

	public String getWind_degrees ()
	{
		return wind_degrees;
	}

	public void setWind_degrees (String wind_degrees)
	{
		this.wind_degrees = wind_degrees;
	}

	public String getPressure_in ()
	{
		return pressure_in;
	}

	public void setPressure_in (String pressure_in)
	{
		this.pressure_in = pressure_in;
	}

	public String getDewpoint_c ()
	{
		return dewpoint_c;
	}

	public void setDewpoint_c (String dewpoint_c)
	{
		this.dewpoint_c = dewpoint_c;
	}

	public String getPressure_mb ()
	{
		return pressure_mb;
	}

	public void setPressure_mb (String pressure_mb)
	{
		this.pressure_mb = pressure_mb;
	}

	public String getIcon ()
	{
		return icon;
	}

	public void setIcon (String icon)
	{
		this.icon = icon;
	}

	public String getLocal_time_rfc822 ()
	{
		return local_time_rfc822;
	}

	public void setLocal_time_rfc822 (String local_time_rfc822)
	{
		this.local_time_rfc822 = local_time_rfc822;
	}

	public String getPrecip_1hr_string ()
	{
		return precip_1hr_string;
	}

	public void setPrecip_1hr_string (String precip_1hr_string)
	{
		this.precip_1hr_string = precip_1hr_string;
	}

	public String getIcon_url ()
	{
		return icon_url;
	}

	public void setIcon_url (String icon_url)
	{
		this.icon_url = icon_url;
	}

	public String getWind_dir ()
	{
		return wind_dir;
	}

	public void setWind_dir (String wind_dir)
	{
		this.wind_dir = wind_dir;
	}

	public String getDewpoint_f ()
	{
		return dewpoint_f;
	}

	public void setDewpoint_f (String dewpoint_f)
	{
		this.dewpoint_f = dewpoint_f;
	}

	public String getNowcast ()
	{
		return nowcast;
	}

	public void setNowcast (String nowcast)
	{
		this.nowcast = nowcast;
	}

//	public Display_location getDisplay_location ()
//	{
//		return display_location;
//	}
//
//	public void setDisplay_location (Display_location display_location)
//	{
//		this.display_location = display_location;
//	}

	public String getVisibility_km ()
	{
		return visibility_km;
	}

	public void setVisibility_km (String visibility_km)
	{
		this.visibility_km = visibility_km;
	}

	public String getTemperature_string ()
	{
		return temperature_string;
	}

	public void setTemperature_string (String temperature_string)
	{
		this.temperature_string = temperature_string;
	}

	public String getLocal_tz_short ()
	{
		return local_tz_short;
	}

	public void setLocal_tz_short (String local_tz_short)
	{
		this.local_tz_short = local_tz_short;
	}

	public String getLocal_epoch ()
	{
		return local_epoch;
	}

	public void setLocal_epoch (String local_epoch)
	{
		this.local_epoch = local_epoch;
	}

	public String getWind_string ()
	{
		return wind_string;
	}

	public void setWind_string (String wind_string)
	{
		this.wind_string = wind_string;
	}

	public String getPrecip_today_in ()
	{
		return precip_today_in;
	}

	public void setPrecip_today_in (String precip_today_in)
	{
		this.precip_today_in = precip_today_in;
	}

	@Override
	public String toString()
	{
		return "Check if you need all of the attrs.";
		//return "ClassPojo [wind_gust_mph = "+wind_gust_mph+", precip_1hr_metric = "+precip_1hr_metric+", precip_today_metric = "+precip_today_metric+", pressure_trend = "+pressure_trend+", forecast_url = "+forecast_url+", history_url = "+history_url+", estimated = "+estimated+", weather = "+weather+", windchill_string = "+windchill_string+", station_id = "+station_id+", UV = "+UV+", observation_epoch = "+observation_epoch+", wind_gust_kph = "+wind_gust_kph+", precip_1hr_in = "+precip_1hr_in+", observation_time = "+observation_time+", feelslike_string = "+feelslike_string+", temp_f = "+temp_f+", local_tz_long = "+local_tz_long+", relative_humidity = "+relative_humidity+", temp_c = "+temp_c+", image = "+image+", solarradiation = "+solarradiation+", visibility_mi = "+visibility_mi+", observation_location = "+observation_location+", wind_mph = "+wind_mph+", heat_index_c = "+heat_index_c+", precip_today_string = "+precip_today_string+", observation_time_rfc822 = "+observation_time_rfc822+", feelslike_f = "+feelslike_f+", heat_index_f = "+heat_index_f+", feelslike_c = "+feelslike_c+", heat_index_string = "+heat_index_string+", ob_url = "+ob_url+", dewpoint_string = "+dewpoint_string+", local_tz_offset = "+local_tz_offset+", wind_kph = "+wind_kph+", windchill_f = "+windchill_f+", windchill_c = "+windchill_c+", wind_degrees = "+wind_degrees+", pressure_in = "+pressure_in+", dewpoint_c = "+dewpoint_c+", pressure_mb = "+pressure_mb+", icon = "+icon+", local_time_rfc822 = "+local_time_rfc822+", precip_1hr_string = "+precip_1hr_string+", icon_url = "+icon_url+", wind_dir = "+wind_dir+", dewpoint_f = "+dewpoint_f+", nowcast = "+nowcast+", display_location = "+display_location+", visibility_km = "+visibility_km+", temperature_string = "+temperature_string+", local_tz_short = "+local_tz_short+", local_epoch = "+local_epoch+", wind_string = "+wind_string+", precip_today_in = "+precip_today_in+"]";
	}
}


class Error
{
	private String description;

	private String type;

	public String getDescription ()
	{
		return description;
	}

	public void setDescription (String description)
	{
		this.description = description;
	}

	public String getType ()
	{
		return type;
	}

	public void setType (String type)
	{
		this.type = type;
	}

	@Override
	public String toString()
	{
		return "ClassPojo [description = "+description+", type = "+type+"]";
	}
}