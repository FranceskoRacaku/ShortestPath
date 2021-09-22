const t = require("dotenv").config({path: __dirname + '/.env'});
//const axios = require("axios"); 
const { Client } = require("@googlemaps/google-maps-services-js");

module.exports.get_geocoding = async (req, res) => {
  // given query string for location, make request to some google maps api to get geocoding for that location 
  // send geocoding + other info back to java client

  // READ url params (country will always be USA)
  // already error-handeled in our java app, and only the city
  // field is required
  let city = req.query.city !== undefined ? req.query.city : "";
  let state = req.query.state !== undefined ? req.query.state : "";

  console.log("city: " + city);
  console.log("state: " + state);
 
  // will hide later when host
  const key = `${process.env.API_KEY}`; 
  const client = new Client({});

  // Make request to google's geocoding api
  client.geocode({
    params:{
      address:`${city} ${state} USA`,
      key: key
    },
    timeout: 1000
  })
  .then(res_g => {
    const lat = res_g.data.results[0].geometry.location.lat; 
    const lng = res_g.data.results[0].geometry.location.lng;
    
    let response = {
      lat: lat, 
      lng: lng,
      success: true, 
    };

    res.status(200).send(response); // send geocode to client 

    console.log(response); 
  })
  .catch(err => {
    console.log(err); 
    response.success = false; 

    res.status(404).send({success: false}); 
  });

}