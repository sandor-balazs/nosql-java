'use strict';

angular.module('ontimeApp').controller('ProjectCountryDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'ProjectCountry', 'ProjectRegion', 'Country', 'Status',
        function($scope, $stateParams, $uibModalInstance, $q, entity, ProjectCountry, ProjectRegion, Country, Status) {

        $scope.projectCountry = entity;
        $scope.projectregions = ProjectRegion.query();
        $scope.countrys = Country.query({filter: 'projectcountry-is-null'});
        $q.all([$scope.projectCountry.$promise, $scope.countrys.$promise]).then(function() {
            if (!$scope.projectCountry.countryId) {
                return $q.reject();
            }
            return Country.get({id : $scope.projectCountry.countryId}).$promise;
        }).then(function(country) {
            $scope.countrys.push(country);
        });
        $scope.statuss = Status.query({filter: 'projectcountry-is-null'});
        $q.all([$scope.projectCountry.$promise, $scope.statuss.$promise]).then(function() {
            if (!$scope.projectCountry.statusId) {
                return $q.reject();
            }
            return Status.get({id : $scope.projectCountry.statusId}).$promise;
        }).then(function(status) {
            $scope.statuss.push(status);
        });
        $scope.load = function(id) {
            ProjectCountry.get({id : id}, function(result) {
                $scope.projectCountry = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:projectCountryUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.projectCountry.id != null) {
                ProjectCountry.update($scope.projectCountry, onSaveSuccess, onSaveError);
            } else {
                ProjectCountry.save($scope.projectCountry, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
