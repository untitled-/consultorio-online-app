'use strict';

angular.module('consultorioOnlineAppApp').controller('DrugDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Drug', 'Treatment',
        function($scope, $stateParams, $uibModalInstance, entity, Drug, Treatment) {

        $scope.drug = entity;
        $scope.treatments = Treatment.query();
        $scope.load = function(id) {
            Drug.get({id : id}, function(result) {
                $scope.drug = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('consultorioOnlineAppApp:drugUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.drug.id != null) {
                Drug.update($scope.drug, onSaveSuccess, onSaveError);
            } else {
                Drug.save($scope.drug, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
