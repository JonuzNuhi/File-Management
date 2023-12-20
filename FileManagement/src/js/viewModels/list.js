/**
 * @license
 * Copyright (c) 2014, 2023, Oracle and/or its affiliates.
 * Licensed under The Universal Permissive License (UPL), Version 1.0
 * as shown at https://oss.oracle.com/licenses/upl/
 * @ignore
 */

define(["knockout", "ojs/ojbootstrap", "text!../singleItemData.json", 
        "ojs/ojarraydataprovider", "ojs/ojknockout", "ojs/ojchart", "ojs/ojtable"], 
function (ko, ojbootstrap_1, data, ArrayDataProvider){
    function ListViewModel() {
      //pie chart
      this.threeDValue = ko.observable("off");
      this.dataProvider1 = new ArrayDataProvider(JSON.parse(data), { keyAttributes: "fileName", });

      //table chart
      this.serviceArray = ko.observableArray([]);
     
      callGetService('list')
      .then((response) =>{
        this.serviceArray(response);
      })
      .catch((error) =>{
        console.log("Error while calling service");
      })
      
    this.dataprovider = new ArrayDataProvider(this.serviceArray, {
        keyAttributes: "downloadUrl",
        implicitSort: [{ attribute: "fileName", direction: "ascending" }],
    });

    this.selectedChangedListener = (event) => {
          const row = event.detail.value.row;
          if (row.values().size > 0) {
              row.values().forEach(function (key) {

                  console.log("selected key: " + key);

                  if (key) {
                    // Construct the URL based on the selected value (adjust the URL structure accordingly)
                    var url = key;
    
                    // Open a new tab with the constructed URL
                    window.open(url, '_blank');
                    
                } else {
                    // Handle the case when no value is selected
                    console.log('Please select a value before clicking the button.');
                }

              });
          }      
  };

    }

    return ListViewModel;
});




