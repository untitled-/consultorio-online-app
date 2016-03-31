'use strict';

angular.module('consultorioOnlineAppApp')
    .controller('LabTestDetailController', function ($scope, $rootScope, $stateParams, entity, LabTest, Consultation) {
        $scope.labTest = entity;
        $scope.load = function (id) {
            LabTest.get({id: id}, function(result) {
                $scope.labTest = result;
            });
        };
        var unsubscribe = $rootScope.$on('consultorioOnlineAppApp:labTestUpdate', function(event, result) {
            $scope.labTest = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
