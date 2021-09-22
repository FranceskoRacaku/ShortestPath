const express = require("express");
const api_router = express.Router();
const search_router = require("./search");

api_router.use("/search", search_router); 

module.exports = api_router; 