'use strict';

angular.module('ontimeApp').controller('EmploymentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Employment',
        function($scope, $stateParams, $uibModalInstance, entity, Employment) {

        $scope.employment = entity;
        $scope.load = function(id) {
            Employment.get({id : id}, function(result) {
                $scope.employment = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:employmentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.employment.id != null) {
                Employment.update($scope.employment, onSaveSuccess, onSaveError);
            } else {
                Employment.save($scope.employment, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
