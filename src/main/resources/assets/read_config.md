How to read & edit config.json file in this game?
----------------------

### "item_list"

"item_list" is the name tag of the data stat struct of all game items.

### "character_list"

"character_list" is the name of the tag that groups the data stats of all game characters.

### GSS

GSS stand for Game Sprite Size, which is 64 pixels. GSS is also the standard unit of this game.

### Game Unit Syntax (GUS Syntax)

"[GSS, 1.3]" means the attribute number is 1.3 times GSS unit.

Use "#", which is the GUS delimiter to separate two or multiple numbers.

The following attribute names each contains multiple number as pair.

* "position": "x-coordinate # y-coordinate"
* "velocity": "x-velocity # y-velocity"
* "size" : "width # height"
* "collision_box": "x-relative-coordinate # y-relative-coordinate # width # height"
