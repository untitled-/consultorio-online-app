'use strict';

angular.module('consultorioOnlineAppApp').controller('TraumaDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Trauma', 'PathologicBkg',
        function($scope, $stateParams, $uibModalInstance, entity, Trauma, PathologicBkg) {

        $scope.trauma = entity;
        $scope.pathologicbkgs = PathologicBkg.query();
        $scope.load = function(id) {
            Trauma.get({id : id}, function(result) {
                $scope.trauma = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineAppApp:traumaUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.trauma.id != null) {
                Trauma.update($scope.trauma, onSaveSuccess, onSaveError);
            } else {
                Trauma.save($scope.trauma, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
