
local ChooseLevelScene = class("ChooseLevelScene", function()
    return display.newScene("ChooseLevelScene")
end)

function ChooseLevelScene:ctor()
    local bg = display.newSprite("menu_bg.png")
    -- make background sprite always align top
    bg:setPosition(display.cx, display.top - bg:getContentSize().height / 2)
    self:addChild(bg)

    -- local title = display.newSprite("#Title.png", display.cx, display.top - 100)
    -- self:addChild(title)

    -- create levels list
    local rect = CCRect(display.left, display.bottom + 180, display.width, display.height - 280)
    self.levelsList = LevelsList.new(rect)
    self.levelsList:addEventListener("onTapLevelIcon", handler(self, self.onTapLevelIcon))
    self:addChild(self.levelsList)

    -- create menu
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

function ChooseLevelScene:onTapLevelIcon(event)
    audio.playSound(data.sound.tapButton)
    app:playLevel(event.levelIndex)
end

function ChooseLevelScene:onEnter()
    self.levelsList:setTouchEnabled(true)
end

return ChooseLevelScene
