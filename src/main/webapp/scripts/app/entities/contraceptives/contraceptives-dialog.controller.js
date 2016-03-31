'use strict';

angular.module('consultorioOnlineAppApp').controller('ContraceptivesDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Contraceptives', 'GynecoobstetricBkg',
        function($scope, $stateParams, $uibModalInstance, entity, Contraceptives, GynecoobstetricBkg) {

        $scope.contraceptives = entity;
        $scope.gynecoobstetricbkgs = GynecoobstetricBkg.query();
        $scope.load = function(id) {
            Contraceptives.get({id : id}, function(result) {
                $scope.contraceptives = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineAppApp:contraceptivesUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.contraceptives.id != null) {
                Contraceptives.update($scope.contraceptives, onSaveSuccess, onSaveError);
            } else {
                Contraceptives.save($scope.contraceptives, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
