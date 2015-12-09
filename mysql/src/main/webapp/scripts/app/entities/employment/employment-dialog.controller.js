'use strict';

angular.module('ontimeApp').controller('EmploymentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Employment', 'Employee', 'Department',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Employment, Employee, Department) {

        $scope.employment = entity;
        $scope.employees = Employee.query();
        $scope.departments = Department.query({filter: 'employment-is-null'});
        $q.all([$scope.employment.$promise, $scope.departments.$promise]).then(function() {
            if (!$scope.employment.departmentId) {
                return $q.reject();
            }
            return Department.get({id : $scope.employment.departmentId}).$promise;
        }).then(function(department) {
            $scope.departments.push(department);
        });
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
