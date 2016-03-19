'use strict';

angular.module('consultorioOnlineUiApp')
    .controller('LabTestDetailController', function ($scope, $rootScope, $stateParams, entity, LabTest, Consultation) {
        $scope.labTest = entity;
        $scope.load = function (id) {
            LabTest.get({id: id}, function(result) {
                $scope.labTest = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineUiApp:labTestUpdate', function(event, result) {
            $scope.labTest = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
