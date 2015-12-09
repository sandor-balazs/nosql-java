'use strict';

angular.module('ontimeApp').controller('SkillDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Skill',
        function($scope, $stateParams, $uibModalInstance, entity, Skill) {

        $scope.skill = entity;
        $scope.load = function(id) {
            Skill.get({id : id}, function(result) {
                $scope.skill = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('ontimeApp:skillUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.skill.id != null) {
                Skill.update($scope.skill, onSaveSuccess, onSaveError);
            } else {
                Skill.save($scope.skill, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
