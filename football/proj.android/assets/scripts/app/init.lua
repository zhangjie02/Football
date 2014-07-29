require("app.config")
require("app.data")
require("framework.init")

require("app.views.BubbleButton")
require("app.views.LevelsList")

require("app.scenes.ShopLayer")
scheduler = require("framework.scheduler")

require("app.MyApp").new():run()