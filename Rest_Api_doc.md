**Rest Api Documentation**

**Show Weather Data**
----
  Returns json data from specific coordinates.

* **URL**

  /api/getWeather

* **Method:**

  `GET`
  
*  **URL Params**

   **Required:**
 
   `userLat=[double]`
   `userLon=[double]`

* **Data Params**

  None

* **Success Response:**

  *
    **Content:** `{"air_temperature_4_3h":273.69501,"windchill_3h":true,"eastward_wind_23_1h":-1.69063,"northward_wind_24_2h":4.97109,"air_temperature_4_1h":273.69501,"windchill_1h":true,"precipitation_amount_353_2h":0,"eastward_wind_23_3h":-1.69063,"eastward_wind_23":-1.69063,"overage_3h":true,"closestLon":30.0,"overage_1h":true,"precipitation_amount_353":0,"northward_wind_24":4.97109,"windchill_air_temp_1h":268.7573577888505,"time_h":0,"windchill_air_temp_3h":268.7573577888505,"overage":true,"northward_wind_24_1h":4.97109,"air_temperature_4_2h":273.69501,"windchill_2h":true,"northward_wind_24_3h":4.97109,"precipitation_amount_353_3h":0,"eastward_wind_23_2h":-1.69063,"overage_2h":true,"message":"Message from JSONHandler.java!","inrange":true,"air_temperature_4":273.69501,"hours since":"2017-11-30 06:00:00","success":true,"precipitation_amount_353_1h":0,"windchill_air_temp_2h":268.7573577888505,"time":257,"windchill":true,"windchill_air_temp":268.7573577888505,"closestLat":60.02941}`
 
* **Error Response:**

  * **Code:** File Not Found <br />
    **Content:** `{ message : "Weather data file was not found on server." }`


* **Sample Call:**

  ```    @RequestMapping(value = "/api/getWeather", method = RequestMethod.GET, produces = "application/json")
  ```
