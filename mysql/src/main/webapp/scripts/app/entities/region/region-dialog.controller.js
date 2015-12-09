'use strict';

angular.module('ontimeApp').controller('RegionDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Region', 'Country',
        function($scope, $stateParams, $uibModalInstance, entity, Region, Country) {

        $scope.region = entity;
        $scope.countrys = Country.query();
        $scope.load = function(id) {
            Region.get({id : id}, function(result) {
                $scope.region = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:regionUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.region.id != null) {
                Region.update($scope.region, onSaveSuccess, onSaveError);
            } else {
                Region.save($scope.region, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
