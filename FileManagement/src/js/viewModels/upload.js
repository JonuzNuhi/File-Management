/**
 * @license
 * Copyright (c) 2014, 2023, Oracle and/or its affiliates.
 * Licensed under The Universal Permissive License (UPL), Version 1.0
 * as shown at https://oss.oracle.com/licenses/upl/
 * @ignore
 */
define(["require", "exports", "knockout", "ojs/ojbootstrap", "ojs/ojfilepickerutils", 
        "ojs/ojarraydataprovider","ojs/ojknockout", "ojs/ojfilepicker", "ojs/ojbutton", "ojs/ojmessages"],
 function(require, exports, ko, Bootstrap, FilePickerUtils, ArrayDataProvider) {
    function UploadViewModel() {

      var self = this;

      this.messagesDataprovider = ko.observableArray([]);
      
      this.fileNames = ko.observable();
      this.selectedFile = ko.observable();

      this.selectListener = (files) => {
        if (files && files.length > 0) {
          this.selectedFile(files[0]);
          this.fileNames([files[0].name]);
        }
      };
      this.selectFiles = (event) => {
          FilePickerUtils.pickFiles(this.selectListener, {
              accept: [],
              capture: "none",
              selectionMode: "single",
          });
      };

      this.buttonAction = (event) => {

        console.log("File Names", this.fileNames()); 
        console.log("SelectedFile", this.selectedFile());

        console.log("hey");

        var input = {
          file : self.selectedFile() 
        }

        callPostService('upload', input)
        .then((response) =>{

        })
        .catch((error) =>{
          console.log("Error while calling service");
          self.messagesDataprovider.push({
            severity: "error",
            summary: "Connection Error",
            detail: "File not uploaded",
            autoTimeout: UTIL.message_timeout,
          });
        })

        // Check if a file is selected
        if (self.selectedFile()) {

          // Assuming you have a function to save the file to the database
          self.messagesDataprovider.push({
            severity: "confirmation",
            summary: "New File",
            detail: "File added successfully",
            autoTimeout: UTIL.message_timeout,
          });
          // saveFileToDatabase(self.selectedFile());
        } else {
          // Handle the case when no file is selected
          self.messagesDataprovider.push({
            severity: "error",
            summary: "Error",
            detail: "Please select a file",
            autoTimeout: UTIL.message_timeout,
          });
        }
  
        return true;
      };

    }


    return UploadViewModel;
  }
);
