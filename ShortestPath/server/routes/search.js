const express = require("express");
const search_router = express.Router(); 

const { get_geocoding } = require("../controllers/search_controller");

search_router.get("/geocoding", get_geocoding); // given query/location string, return (lat,long) and other city info


module.exports = search_router; 