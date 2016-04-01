---
layout: page
title: Mapping Tool
excerpt: 
modified: 2014-08-08T19:44:38.564948-04:00
image:
  feature: town_01.png
---

This application is used to process the XML file generated by the CDF Converter Tool, assigns metrics to objects of the metropolis and generates an output XML that is ready to be used by CodeMetropolis Placing Tool. The tool is using the mapping file to link source code elements and metrics to world objects. For example, link of the LOC metric is related to the height of the building. The *source* tag contains the metric that will be a link to the object, the *name* parameter is the element of the source code and the *from* parameter is the metric. The *target* parameter is the world object, the *name* parameter is the name of the element in the city and the *to* parameter is the property of the element. If the value of the metric doesn't fit the world element, you have to use conversion. 

**Usage**: `java -jar mapping.jar -i <inputFile> -m <mappingFile> [-o <outputFile>]`

**Command line options:**  

* `-h`: help, shows the usage of the command line tool.  
* `-i`: input, the path of the input XML file generated by the Converter Tool. Required.  
* `-o`: output, which will be generated to the given path. Default: "mappingToPlacing.xml".  
* `-m`: mapping, path of the input mapping file. Required.  
* `-s`: scale. Set the scale of blocks. Default: 1.0  
* `-v`: validate. Default: false. In case of true, the tool will validate the generated structure elements, if it's false, the invalid structure elements will be thrown. 

**About the mapping file**  

The mapping file contains the parameters of build up the virtual world from the source code. 

~~~ xml
<linking source="method" target="floor">
	<binding from="LLOC" to="height"/>
	<binding from="NII" to="width"/>
	<binding from="NOI" to="length"/>
	<binding from="McCC" to="character">
		<conversions>
			<conversion type="quantization">
				<parameter name="level0" value="glass"/>
				<parameter name="level1" value="sand"/>
				<parameter name="level2" value="planks"/>
				<parameter name="level3" value="stone"/>
				<parameter name="level4" value="obsidian"/>
			</conversion>
		</conversions>
    </binding>
</linking>
~~~

In the `<linking>` tag it can be set how a program element should be displayed in the world. The user can define the attributes of it like LOC, NUMPAR or McCabe complexity with `<binding>` tag, choose a property of the graphical object of it (for example height or length of floor) which was defined in the `<linking>` tag. Parameters also can be customized with giving a name and a value in a `<conversion>` parent tag, like in the following example:

~~~ xml  
<conversion type="quantization">
	<parameter name="level0" value="1"/>
	<parameter name="level1" value="2"/>
</conversion>
~~~

[sm]: <https://www.sourcemeter.com/>