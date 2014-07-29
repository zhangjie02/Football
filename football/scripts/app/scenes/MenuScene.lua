
local MenuScene = class("MenuScene", function()
    return display.newScene("MenuScene")
end)

function MenuScene:ctor()
    -- ui.newTTFLabel({text = "Hello, World", size = 64, align = ui.TEXT_ALIGN_CENTER})
    --     :pos(display.cx, display.cy)
    --     :addTo(self)
    
    audio.playMusic(data.sound.bg,true)

    self.bg = display.newSprite("ui_bg.png", display.cx, display.cy)
    self:addChild(self.bg)

    self.title = display.newSprite("title.png",display.cx,display.height-200)
    self:addChild(self.title)

    -- create menu
    self.moreGamesButton = BubbleButton.new({
        image = "menu_more.png",
        x = display.left + 150,
        y = display.bottom + 250,
        sound = data.sound.tapButton,
        prepare = function()
            self.menu:setEnabled(false)
        end,
        listener = function()
            app:enterHelpScene()
        end,
    })

    self.startButton = BubbleButton.new({
        image = "menu_start.png",
        x = display.right - 150,
        y = display.bottom + 250,
        sound = data.sound.tapButton,
        prepare = function()
            self.menu:setEnabled(false)
        end,
        listener = function()
            app:enterChooseLevelScene()
        end,
    })

    self.shopButton = BubbleButton.new({
        image = "shop.png",
        x = display.cx,
        y = display.bottom + 150,
        sound = data.sound.tapButton,
        prepare = function()
            self.menu:setEnabled(false)
        end,
        listener = function()
            app:enterShopScene()
        end,
    })

    self.menu = ui.newMenu({self.moreGamesButton, self.startButton})
    self:addChild(self.menu)
    self:getRecord()
end

function MenuScene:getRecord()
    data.record_level_index = CCUserDefault:sharedUserDefault():getIntegerForKey("record_level_index")
    if data.record_level_index == 0 then
    	data.record_level_index = 1
    end
    data.num_lf = CCUserDefault:sharedUserDefault():getIntegerForKey("num_lf")
    
end

function MenuScene:onEnter()
end

function MenuScene:onExit()
end

return MenuScene
