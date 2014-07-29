
local Levels = import("..data.Levels")
local Board = import("..views.Board")

local PlayLevelScene = class("PlayLevelScene", function()
    return display.newScene("PlayLevelScene")
end)

function PlayLevelScene:ctor(levelIndex)

    self.levelIndex = levelIndex
    local bg = display.newSprite("menu_bg.png")
    -- make background sprite always align top
    bg:setPosition(display.cx, display.top - bg:getContentSize().height / 2)
    self:addChild(bg)

    self.board = Board.new(Levels.get(levelIndex))
    self.board:addEventListener("LEVEL_COMPLETED", handler(self, self.onLevelCompleted))
    self.board:addEventListener("USE_PROP", handler(self, self.onUseProp))
    self:addChild(self.board)

    local label = ui.newBMFontLabel({
        text  = string.format("Level: %s", tostring(levelIndex)),
        font  = "UIFont.fnt",
        x     = display.left + 10,
        y     = display.bottom + 120,
        align = ui.TEXT_ALIGN_LEFT,
    })
    self:addChild(label)

    self.prop_sp = display.newSprite("lf_01.png",display.left + 270,display.bottom + 130)
    :addTo(self)
    self.prop_sp:setTouchEnabled(true)

    -- local frames = display.newFrames("lf_%02d.png", 1, 2)
    local frames1 = {}
    frames1[1] = CCSpriteFrame:create("lf_01.png",CCRectMake(0, 0, 120, 140))
    frames1[2] = CCSpriteFrame:create("lf_02.png",CCRectMake(0, 0, 120, 140))
    local animation1 = display.newAnimation(frames1, 0.7 / 2) -- 0.5 秒播放 2 桢
    local action1 = CCAnimate:create(animation1)
    self.rep1 = CCRepeatForever:create(action1)

    local frames2 = {}
    frames2[1] = CCSpriteFrame:create("lf_001.png",CCRectMake(0, 0, 120, 140))
    frames2[2] = CCSpriteFrame:create("lf_002.png",CCRectMake(0, 0, 120, 140))
    local animation2 = display.newAnimation(frames2, 0.7 / 2) -- 0.5 秒播放 2 桢
    local action2 = CCAnimate:create(animation2)
    self.rep2 = CCRepeatForever:create(action2)

    self.prop_sp:runAction(self.rep1)
    -- self.prop_sp:setVisible(false)

    self.prop_sp2 = display.newSprite("lf_01.png",display.left + 270,display.bottom + 130)
    :addTo(self)
    self.prop_sp2:setTouchEnabled(true)
    self.prop_sp2:runAction(self.rep2)
    self.prop_sp2:setVisible(false)

    self.prop_sp:addNodeEventListener(cc.NODE_TOUCH_EVENT,function(event)
        if event.name == "began" then
            return true
        end
        if event.name == "ended" then
            if data.num_lf > 0 then
                self.board.is_prop = not self.board.is_prop
                if self.board.is_prop == true then
                    -- self.prop_sp:runAction(self.rep2)
                    self.prop_sp:setVisible(false)
                    self.prop_sp2:setVisible(true)
                else
                    self.prop_sp:setVisible(true)
                    self.prop_sp2:setVisible(false)
                end
            else
                -- self:shop_touch()
                self:showJavaToast("下载广告中显示的应用或游戏，激活后可获得小手指道具")
            end
            print("dfjkdjfkdjfk")
        end
    end)

    self.prop_sp2:addNodeEventListener(cc.NODE_TOUCH_EVENT,function(event)
        if event.name == "began" then
            return true
        end
        if event.name == "ended" then
            if data.num_lf > 0 then
                self.board.is_prop = not self.board.is_prop
                if self.board.is_prop == true then
                    -- self.prop_sp:runAction(self.rep2)
                    self.prop_sp:setVisible(false)
                    self.prop_sp2:setVisible(true)
                else
                    self.prop_sp:setVisible(true)
                    self.prop_sp2:setVisible(false)
                end
            else
                -- self:shop_touch()
                self:showJavaToast("下载广告中显示的应用或游戏，激活后可获得小手指道具")
            end
            print("dfjkdjfkdjfk")
        end
    end)

    PlayLevelScene.label1 = ui.newBMFontLabel({
        text  = ":  "..data.num_lf,
        font  = "UIFont.fnt",
        x     = display.left + 320,
        y     = display.bottom + 120,
        align = ui.TEXT_ALIGN_LEFT,
    })
    self:addChild(PlayLevelScene.label1)
    -- self.label1:setVisible(false)

    -- create menu
    local backButton = ui.newImageMenuItem({
        image = "btn_back.png",
        imageSelected = "btn_back.png",
        x = display.right - 100,
        y = display.bottom + 120,
        sound =  data.sound.backButton,
        listener = function()
            app:enterChooseLevelScene()
        end,
    })

    local restartButton = ui.newImageMenuItem({
        image = "restart.png",
        imageSelected = "restart.png",
        x = display.right - 100,
        y = display.top - 220,
        sound =  data.sound.backButton,
        listener = function()
            app:playLevel(levelIndex)
        end,
    })

    local shopButton = ui.newImageMenuItem({
        image = "btn_shop.png",
        imageSelected = "btn_shop.png",
        x = display.left + 100,
        y = display.top - 220,
        sound =  data.sound.backButton,
        listener = function()
            self:shop_touch()
        end,
    })

    local menu = ui.newMenu({backButton,restartButton})
    self:addChild(menu)

    schedule_update = scheduler.scheduleUpdateGlobal(handler(self,self.update))
end

function PlayLevelScene:update()
    PlayLevelScene.label1:setString(":  "..data.num_lf)
    if data.num_lf <= 0 then
        self.board.is_prop = false
    end
    -- if self.board.is_prop == true then
    --     self.prop_sp:runAction(self.rep2)
    -- else
    --     self.prop_sp:runAction(self.rep1)
    -- end
end

function PlayLevelScene:showJavaToast(__str)
    local javaClassName = "com/wade/football/Football"
    local javaMethodName = "showTip"
    local javaParams = {
       __str
    }
    local javaMethodSig = "(Ljava/lang/String;)V"
    luaj.callStaticMethod(javaClassName, javaMethodName, javaParams, javaMethodSig)
end

function PlayLevelScene:shop_touch()
    local net_status = CCNetwork:getInternetConnectionStatus()
    print("net:"..net_status)
    if net_status == kCCNetworkStatusNotReachable then
        self:showJavaToast("无效的网络连接！")
    else
        self.shopLayer = display.newLayer()
        :addTo(self)
        shop = ShopLayer.new()
        :addTo(self.shopLayer)

        self.btn_close = display.newSprite("close.png", display.cx, display.bottom + 120)
        :addTo(self.shopLayer)
        self.btn_close:setTouchEnabled(true)
        self.btn_close:addNodeEventListener(cc.NODE_TOUCH_EVENT,function(event)
            print("event:"..event.name)
            if event.name == "began" then
                print("wade hahhaha1")
                return true
            end
            if event.name == "ended" then
                self.prop_sp:setVisible(true)
                self.prop_sp2:setVisible(false)
                self:removeChild(self.shopLayer, false)
            end
        end)
    end
end

function PlayLevelScene:onUseProp()
    data.num_lf = data.num_lf -1
    CCUserDefault:sharedUserDefault():setIntegerForKey("num_lf",data.num_lf)
    if data.num_lf == 0 then
        -- self:shop_touch()
        self:showJavaToast("下载广告中显示的应用或游戏，激活后可获得小手指道具")
    end
end

function PlayLevelScene:onLevelCompleted()
    audio.playSound(data.sound.levelCompleted)

    local dialog = display.newSprite("Complete.png")
    dialog:setPosition(display.cx, display.top + dialog:getContentSize().height / 2 + 40)
    self:addChild(dialog)

    transition.moveTo(dialog, {time = 0.7, y = display.top - dialog:getContentSize().height / 2 - 40, easing = "BOUNCEOUT"})
    
    dialog:setTouchMode(cc.TOUCH_MODE_ONE_BY_ONE)
    dialog:setTouchEnabled(true)
    dialog:addNodeEventListener(cc.NODE_TOUCH_EVENT,function(event)
    	if event.name == "began" then
    		print("wade NODE_TOUCH_EVENT")
    		return true
    	end
    	if event.name == "ended" then
    		data.cur_level_index = data.cur_level_index+1
    		if data.cur_level_index > data.record_level_index then
    			data.record_level_index = data.cur_level_index
    			CCUserDefault:sharedUserDefault():setIntegerForKey("record_level_index", data.record_level_index)
    		end
    		app:playLevel(data.cur_level_index)
    	end
    end)
end

--从java返回，增加小手指的数量
function addLf(__arg)
            if __arg == "one" then
                        data.num_lf = data.num_lf + 1
                        print("addLf one")
            elseif __arg == "five" then
                        print("addLf five")
                        data.num_lf = data.num_lf + 6
            end
            
            print("addLf finish")
end

function PlayLevelScene:onEnter()
end

return PlayLevelScene
