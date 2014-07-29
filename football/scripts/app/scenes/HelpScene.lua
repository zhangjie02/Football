
local HelpScene = class("HelpScene", function()
    return display.newScene("HelpScene")
end)

function HelpScene:ctor()
    self.bg = display.newSprite("help_bg.png", display.cx, display.cy)
    self:addChild(self.bg)

    self.about_title = ui.newTTFLabel({text = "游戏帮助", size = 64
                ,align = ui.TEXT_ALIGN_CENTER})
                :pos(display.cx, display.height-150)
    self.about_title:setColor(ccc3(255,255,255))
    -- self.about_title:addTo(self)

    self.about_txt = ui.newTTFLabel({text = "游戏中将所有的足球变成\n美女，即可进行下一关",size=50
    	,align = ui.TEXT_ALIGN_CENTER})
    	:pos(display.cx, display.height-350)
    self.about_txt:setColor(ccc3(255,255,0))
    -- self.about_txt:addTo(self)

    self.other_txt = ui.newTTFLabel({text = "联系QQ:1063776603",size=50
    	,align = ui.TEXT_ALIGN_CENTER})
    	:pos(display.cx, display.height-550)
    self.other_txt:setColor(ccc3(230,51,173))
    -- self.other_txt:addTo(self)

    local backButton = ui.newImageMenuItem({
        image = "btn_back.png",
        imageSelected = "btn_back.png",
        x = display.right - 100,
        y = display.bottom + 120,
        sound = data.sound.backButton,
        listener = function()
            app:enterMenuScene()
        end,
    })
    local menu = ui.newMenu({backButton})
    self:addChild(menu)
end

function HelpScene:onEnter()
end

function HelpScene:onExit()
end

return HelpScene
