'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('PatientController', function ($scope, $state, Patient, PatientSearch) {

        $scope.patients = [];
        $scope.loadAll = function() {
            Patient.query(function(result) {
               $scope.patients = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            PatientSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.patients = result;
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
            $scope.patient = {
                firstName: null,
                middleName: null,
                lastName: null,
                dateOfBirth: null,
                job: null,
                bloodType: null,
                maritalStatus: null,
                gender: null,
                id: null
            };
        };
    });
