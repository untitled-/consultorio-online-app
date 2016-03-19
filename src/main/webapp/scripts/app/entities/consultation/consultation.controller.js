'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('ConsultationController', function ($scope, $state, Consultation, ConsultationSearch) {

        $scope.consultations = [];
        $scope.loadAll = function() {
            Consultation.query(function(result) {
               $scope.consultations = result;
            });
        };
        $scope.loadAll();


        $scope.search = function () {
            ConsultationSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.consultations = result;
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
            $scope.consultation = {
                consultationDate: null,
                idx: null,
                diferentialDiagnostic: null,
                id: null
            };
        };
    });
