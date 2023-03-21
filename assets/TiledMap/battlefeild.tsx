<?xml version="1.0" encoding="UTF-8"?>
<tileset version="1.9" tiledversion="1.9.2" name="wall" tilewidth="18" tileheight="18" tilecount="30" columns="6">
 <image source="../../../Pixel Dawings/Tanks/wall.png" width="108" height="90"/>
 <tile id="0">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="8" width="10" height="10"/>
  </objectgroup>
 </tile>
 <tile id="1">
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="8" width="18" height="10"/>
  </objectgroup>
 </tile>
 <tile id="2">
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="8" width="10" height="10"/>
  </objectgroup>
 </tile>
 <tile id="6">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="0" width="10" height="18"/>
  </objectgroup>
 </tile>
 <tile id="7">
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="18" height="18"/>
  </objectgroup>
 </tile>
 <tile id="8">
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="10" height="18"/>
  </objectgroup>
 </tile>
 <tile id="12">
  <objectgroup draworder="index" id="2">
   <object id="1" x="8" y="0" width="10" height="10"/>
  </objectgroup>
 </tile>
 <tile id="13">
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="18" height="10"/>
  </objectgroup>
 </tile>
 <tile id="14">
  <objectgroup draworder="index" id="2">
   <object id="1" x="0" y="0" width="10" height="10"/>
  </objectgroup>
 </tile>
 <tile id="18">
  <objectgroup draworder="index" id="2">
   <object id="2" x="10" y="10">
    <polygon points="-0.03125,-0.0625 7.94531,-0.0664063 7.97266,-10.0625 -10.0243,-10.0514 -10.0444,7.97014 -0.03125,7.94141"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="19">
  <objectgroup draworder="index" id="3">
   <object id="2" x="8" y="10">
    <polygon points="0.03125,-0.0625 -7.94531,-0.0664063 -7.97266,-10.0625 10.0243,-10.0514 10.0444,7.97014 0.03125,7.94141"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="24">
  <objectgroup draworder="index" id="2">
   <object id="1" x="10" y="8">
    <polygon points="-0.03125,0.0625 7.94531,0.0664063 7.97266,10.0625 -10.0243,10.0514 -10.0444,-7.97014 -0.03125,-7.94141"/>
   </object>
  </objectgroup>
 </tile>
 <tile id="25">
  <objectgroup draworder="index" id="2">
   <object id="2" x="8" y="8">
    <polygon points="0.03125,0.0625 -7.94531,0.0664063 -7.97266,10.0625 10.0243,10.0514 10.0444,-7.97014 0.03125,-7.94141"/>
   </object>
  </objectgroup>
 </tile>
 <wangsets>
  <wangset name="wall" type="corner" tile="-1">
   <wangcolor name="void" color="#ff0000" tile="-1" probability="1"/>
   <wangcolor name="wall" color="#00ff00" tile="-1" probability="1"/>
   <wangcolor name="sand" color="#0000ff" tile="-1" probability="1"/>
   <wangtile tileid="0" wangid="0,1,0,2,0,1,0,1"/>
   <wangtile tileid="1" wangid="0,1,0,2,0,2,0,1"/>
   <wangtile tileid="2" wangid="0,1,0,1,0,2,0,1"/>
   <wangtile tileid="3" wangid="0,3,0,3,0,3,0,3"/>
   <wangtile tileid="4" wangid="0,3,0,3,0,3,0,3"/>
   <wangtile tileid="5" wangid="0,3,0,3,0,3,0,3"/>
   <wangtile tileid="6" wangid="0,2,0,2,0,1,0,1"/>
   <wangtile tileid="7" wangid="0,2,0,2,0,2,0,2"/>
   <wangtile tileid="8" wangid="0,1,0,1,0,2,0,2"/>
   <wangtile tileid="9" wangid="0,3,0,3,0,3,0,3"/>
   <wangtile tileid="10" wangid="0,3,0,3,0,3,0,3"/>
   <wangtile tileid="11" wangid="0,3,0,3,0,3,0,3"/>
   <wangtile tileid="12" wangid="0,2,0,1,0,1,0,1"/>
   <wangtile tileid="13" wangid="0,2,0,1,0,1,0,2"/>
   <wangtile tileid="14" wangid="0,1,0,1,0,1,0,2"/>
   <wangtile tileid="18" wangid="0,2,0,1,0,2,0,2"/>
   <wangtile tileid="19" wangid="0,2,0,2,0,1,0,2"/>
   <wangtile tileid="24" wangid="0,1,0,2,0,2,0,2"/>
   <wangtile tileid="25" wangid="0,2,0,2,0,2,0,1"/>
  </wangset>
 </wangsets>
</tileset>
