
local ShopScene = class("ShopScene", function()
    return display.newScene("ShopScene")
end)

function ShopScene:ctor()
    -- ui.newTTFLabel({text = "Hello, World", size = 64, align = ui.TEXT_ALIGN_CENTER})
    --     :pos(display.cx, display.cy)
    --     :addTo(self)

    -- cc.ui.UIImage.new("shop_bg.png", {scale9 = true})
    --     :setLayoutSize(640, 500)
    --     :align(display.CENTER_TOP, display.cx, display.height)
    --     :addTo(self)
    -- create menu
    self.bg = display.newSprite("shop_meinv.png", display.cx, display.cy)
    :addTo(self)

    shopLayer = ShopLayer.new()
    -- local shop_x = (display.width-shopLayer.bg_size.width)/2
    -- local shop_y = (display.height-shopLayer.bg_size.height)/2
    -- shopLayer:setPosition(ccp(shop_x,shop_y))
    self:addChild(shopLayer)

    local backButton = ui.newImageMenuItem({
        image = "btn_back.png",
        imageSelected = "btn_back.png",
        x = display.right - 100,
        y = display.bottom + 120,
        sound =  data.sound.backButton,
        listener = function()
            app:enterMenuScene()
        end,
    })

    local menu = ui.newMenu({backButton})
    self:addChild(menu)

end

function ShopScene:onEnter()
end

function ShopScene:onExit()
end

return ShopScene
