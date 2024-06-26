# Outline Project
Software Development Plan
- **Start date**: 19.01.2023
- **Due date**: 14.04.2023
- **Author**: @k1801606
- **Collaborators**: null
- **Repository**: https://gitlab.com/KUGitlab/ci5105/portfolio2022/K1801606/Outliner

# Outline Software Development Plan

This is an app which should allow a user to create lists of different categories using words, moving them around, assigning them to a user and taging. This app can be used to crate a shoping list, team composition list or list of animal types.

The app must be multi-purposful and allow the user to determine the list feel.

# Requirments

<table>
<tr>
	<th> Must Have  </th>
	<th> R1 - Structured Data Model ✓ </th>
	<th> R2 - Use Best Practises ✓ </th>
	<th> R3 - Add New Section ✓ </th>
	<th> R4 - Add a Sub Section ✓ </th>
</tr>
<tr>
	<th> Should Have  </th>
	<th> R5 - Documentation ✓ </th>
	<th> R6 - Implement GUI ✓ </th>
	<th> R7 - OO Design ✓ </th>
	<th> R8 - Edit Section ✓</th>
	<th> R9 - Delete Section ✓</th>
	<th> R10 - Mark Section Complete ✓</th>
	<th> R11 - Edit Subsection ✓ </th>
	<th> R12 - Delete Subsection  ✓</th>
	<th> R13 - Save to a file ✓</th>
	<th> R14 - Load from a file ✓</th>
</tr>
<tr>
	<th> Could Have </th>
	<th> R15 - Search func </th>
	<th> R16 - Add a tag ✓</th>
	<th> R17 - Edit a tag ✓</th>
	<th> R18 - Sort Sections </th>
	<th> R19 - Reorder Sections ✓</th>
	<th> R20 - Retrieve data from Web </th>
	<th> R21 - Format Text </th>
</tr>
<tr>
	<th> Won't Have  </th>
	<th> Admin Functionality </th>
</tr>
</table>

# Detailed Requirements

| **R1** | **Structured Data Model**|
| ------ | ------------------------ |
| **Description** | The project must conform to the UML diagram created and be able to store and retrieve data. It should be developed in NetBeans using Swing for the GUI elements and not use any frameworks |
| **Design notes** | |
| | - The project must implement all the must haves, most of the should haves, some of the could haves and none of the won't haves|
| | - The project have been developed with 3 types of components, Model to support the class instanciation, View with Swing to allow user to interact with software and the controller which is the Outliner class which has most static methods to allow the objects to be switched and operated on|
| | - The data model is fully capable to support the application main functions |



| **R2** | **Use Best Practises**|
| ------ | ------------------------ |
| **Description** | MVC , GitLab, Getters and Setters |
| **Design notes** | |
| | - MVC has been used to make the application modular, View can be re-done without breaking model if all attributes are connected correctly|
| | - Git and Gitlab were used during the development process to allow commits and gradual change to the application, which allowed to add features and in theory if need be support going to previous more stable version |
| | - Java specific good practises were used such as private attributes and public getters and setters, as well as a good use of instances and statics  |

| **R3** | **Add new section**|
| ------ | ------------------------ |
| **Description** | The user of the outline should be able to add the top level section which can hold child subsections and contain the attributes which need to be displayed to the user |
| **Pre-condition** | New section can be created with no sections present or with some sections present |
| **Post-condition** | After a new section is created it is selected to make it easier for the user to start typing or editing the new section |
| **Design notes** | |
| | - There is no big destinction between a section and a sub section, it is a recursive relationship |
| | - Priority is unused and is right now random |
| | |
| **Parameters**| The section needs TEXT, USER, TAGS and PRIORITY as default |
| **Success** | |
| | - User can create section with no sections present |
| | - User can create section while selecteing other sections |
| | - User can create as many sections as neceserry|
| **Tests** | |
| | - Test that user cannot create section where the required parametrs are not set |
| | - Test that user has easy access to the new section created |
| | - Test that the new section is visible to the user|
| **Errors** | |
| | When no section is selected and user creates new section, no section is selected |



| **R4** | **Add a subsection**|
| ------ | ------------------------ |
| **Description** | A child subsection needs to be appended to the parent section and some text needs to be stored and diplayed to the user |
| **Pre-condition** | There needs to be another section present which can take a subsection as parent section |
| **Post-condition** | After a subsection is created it needs to stick with it's parent section |
| **Design notes** | |
| | Word-like outline type has been selected, so sub-sections need to be created as sections and moved to a parent section |
| | Subsection can also be created by selecting other subsections |
| | |
| **Parameters**| The section needs TEXT, USER, TAGS and PRIORITY as default |
| **Success** | |
| | - User can create a subsection when other section is present|
| | - User can edit attributes of the subsection |
| **Tests** | |
| | - Test that user cannot have a sub-section with no parent section |
| | - Test that the user can edit sub-section text |

| **R5** | **Documentation**|
| ------ | ------------------------ |
| **Description** | The project should be well documented, which allows better planing for the project requirements and expected outcomes |


| **R6** | **Implement GUI**|
| ------ | ------------------------ |
| **Description** | The user needs to be able to interact with the app using Graphical User Interface elements using the keyboard to create and move sections, as well as using the mouse to move sections around |
| **Pre-condition** | the GUI needs to be able to access the model through the controller |
| **Post-condition** | After accessing the model it needs to diplay relevant informtion to the user and allow the user to modify the model through the controller |
| **Design notes** | |
| | Outliner static class is the controller, which is accessed by the GUI |
| **Parameters**| The model needs to be given as argument for the GUI to display the information |
| **Success** | |
| | - User can use the GUI to modify the Outline|
| | - User can change the model attributes with ease |
| **Tests** | |
| | - Test that the GUI doesn't crash the application form the use input |
| **Errors** | |
| | - While many test have been done stability, some user inputs still crash the program or make it behave sub-optimally |

| **R7** | **OO Design**|
| ------ | ------------------------ |
| **Description** | The project must use OO oriented model. This should make use of java classes with private attributes, getter and setter methods, other methods and objects created using these classes |
| **Design notes** | |
| | - Application must be built using Object-oriented approach where classes are built and objects are instancieted from these classes |
| | - These objects need to change state and attribute values to store the chnages made |

| **R8** | **Edit Section**|
| ------ | ------------------------ |
| **Description** | A section needs be updated by the user using the GUI elements with ability to change attributes of the section |
| **Pre-condition** | a Section needs to be present for it to be edited and changes need to be visible to the user |
| **Post-condition** | After a section is chnaged the user needs to be able to see the change and be able to repeat the process |
| **Parameters**| The VALUE which need to be changed |
| **Success** | |
| | - User can edit a section |
| | - User can see the change they made |
| | - User can reverse the change they made|

| **R9** | **Delete Section**|
| ------ | ------------------------ |
| **Description** | A sections needs to be deleted by the user using the GUI elements with deletion of the child objects |
| **Pre-condition** | A section need to be present to delete it |
| **Post-condition** | A section need to disapear and not be saved in memory after proram closes |
| **Design notes** | |
| **Parameters**| parameter would be ID of the ection to delete |
| **Success** | |
| | - User can delete a section |
| | - Deleted section is not visible |
| | - Deleted section can be brought back if deleted by mistake |


| **R10** | **Mark Section Complete**|
| ------ | ------------------------ |
| **Description** | The user should be able to mark a section as complete or done, this should be diplayed to the user and undone if neccesary |
| **Pre-condition** | the section needs to be present in the model |
| **Post-condition** | the complete tag needs to be displayed to the user |
| **Design notes** | |
| **Success** | |
| | - Section can be marked as complete |
| | - The change is displayed to the user |
| | - Section that is complete can be un-completed|


| **R11** | **Edit Subsection**|
| ------ | ------------------------ |
| **Description** | The user should be able to edit a subsection attributes using GUI elements ( SUB SECTION HAS SAME MODEL AS SECTION ! ) |



| **R12** | **Delete Subsection**|
| ------ | ------------------------ |
| **Description** | The user should be able to delete a subsection and all child elements using the GUI elements ( SUB SECTION HAS SAME MODEL AS SECTION ! ) |


| **R13** | **Save to a file**|
| ------ | ------------------------ |
| **Description** | The user should be able to save a file storing the outline with all sections and subsections to access in another session using the GUI elements |
| **Pre-condition** | The Outline should have a name for the file to be named with the same name |
| **Post-condition** | The Outline should be stored with the program, so that later it can be loaded to be edited |
| **Design notes** | |
| | For right now the project saves Outline files in the target folder of the project, however it will be easy to change in future |
| | |
| | |
| **Parameters**| |
| **Success** | |
| | - An Outline can be saved to be edited later |
| | - A file created is efficient at storing the program data|
| **Tests** | |
| | Outline saved and loaded are identical |
| | Outline save doesn't crash the application |
| **Errors** | |
| | When outline is saved it sometimes saves the character that is used to type |
| | if the user uses Outline name which violates OS specific file name rules, the app may crash|


| **R14** | **Load from a file**|
| ------ | ------------------------ |
| **Description** | The user should be able to load the outline from a file with all sections and subsections loaded and edit the outline using the GUI elements |
| **Pre-condition** | The file to load need to have already been created and put in the appropriate load folder |
| **Post-condition** | The outline is loaded and is presented to the user and can be edited |
| **Design notes** | |
| | |
| | |
| | |
| **Parameters**| The NAME of the outline to be loaded is required |
| **Step sequence** | |
| | - Folder is searched for the appropriate JSON file |
| | - Matching file is loaded as an object|
| | - Object is loaded to the controller and the GUI ready for editing |
| **Success** | |
| | - Saved Outline can be accessed and edited |
| | - Edited Outline can be save again|


| **R15** | **Search Function**|
| ------ | ------------------------ |
| **Description** | Simple search function which uses some text inputed by the user using the GUI to compare this to text attributes of the section and subsections and return a view sorting the elements to have matching sections most visible |
| **Pre-condition** | Some sections need to exist for the search to work |
| **Post-condition** | The sections which are found need to be presented to the user |
| **Design notes** | |
| **Parameters**| search VALUE needs to be typed in by the user |
| **Step sequence** | |
| | - RegexRules are set |
| | - All Sections are checked for match |
| | - First match is selected |
| **Success** | |
| | The outline which matches the text is found and selected |
| | The outline which matches the user is found and selected |


| **R16** | **Add a tag**|
| ------ | ------------------------ |
| **Description** | The user should be able to add a tag which can identify groups of sections and subsections which are visible to the user in the GUI elements |
| **Pre-condition** | the Section needs to exist to hold a tag |
| **Post-condition** | the section tag needs to be visible to the user |
| **Design notes** | |
| **Parameters**| The string VALUE to be added as a tag |
| **Step sequence** | |
| | - Get the tag string from the user |
| | - Call the method to add it to the Section |
| | - Display new tag to the user |
| **Success** | |
| | - User can add a tag to the Outline |
| | - User can add multiple tags to the same outline |
| **Tests** | |
| | - User cannot add empty Tags|
| | - User cannot add the same tag twice |
| | - User can add multyple tags in one call |
| **Errors** | |
| | - User can add same tag multiple times and then they need to be removed one by one |
| | - Only one tag can be added at a time |


| **R17** | **Edit a tag**|
| ------ | ------------------------ |
| **Description** | The user should be able to edit a tag, editing the text of the tag |
| **Pre-condition** | The tag needs to be already created to edit it |
| **Post-condition** | the tag text needs to change and diplayed to the user with the change |
| **Design notes** | |
| | In my approach you can edit tag by deleting it and just making a new one, it is slightly longer but functional |
| **Parameters**| The string VALUE to be edited as a tag |


| **R18** | **Sort Sections**|
| ------ | ------------------------ |
| **Description** | The user should be able to sort the sections and subsections to be diplayed in different order in the GUI elements |
| **Status** | Canceled for the scope of this project due to time constraints |


| **R19** | **Reorder Sections**|
| ------ | ------------------------ |
| **Description** | The user should be able to reorder sections and subsections also moving all the child elements with the parent element |
| **Pre-condition** | There need to be multiple sections present to move the around |
| **Post-condition** | the section is moved to the appropriate position and it is displayed to the user |
| **Design notes** | |
| | - There are two types of movement 1. Within a parent section 2. To a new parent or to an existing parent sections |
| **Step sequence** | |
| | - Determine where the user want section to be moved to |
| | - Move the section and align the attributes with this change |
| | - Present the change in the View |
| **Success** | |
| | - Sections can be moved horizontally |
| | - Section can be moved vertically|
| **Tests** | There are many possible errors, test that rearanging does not crash the app |


| **R20** | **Retrieve data from Web**|
| ------ | ------------------------ |
| **Description** | Get data from the web and map it onto sections and subsections? |
| **Status** | Canceled for the scope of this project due to time constraints |


| **R21** | **Format Text**|
| ------ | ------------------------ |
| **Description** | The user can edit how the text is displayed, color, font size and toher features |
| **Status** | Canceled for the scope of this project due to time constraints |