'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('TreatmentController', function ($scope, $state, Treatment, TreatmentSearch) {

        $scope.treatments = [];
        $scope.loadAll = function() {
            Treatment.query(function(result) {
               $scope.treatments = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            TreatmentSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.treatments = result;
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
            $scope.treatment = {
                prescriptionNumber: null,
                id: null
            };
        };
    });
