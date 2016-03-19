'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('LabTestController', function ($scope, $state, LabTest, LabTestSearch) {

        $scope.labTests = [];
        $scope.loadAll = function() {
            LabTest.query(function(result) {
               $scope.labTests = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            LabTestSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.labTests = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.labTest = {
                name: null,
                description: null,
                id: null
            };
        };
    });
