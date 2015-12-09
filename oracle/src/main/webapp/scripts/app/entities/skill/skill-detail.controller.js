'use strict';

angular.module('ontimeApp')
    .controller('SkillDetailController', function ($scope, $rootScope, $stateParams, entity, Skill) {
        $scope.skill = entity;
        $scope.load = function (id) {
            Skill.get({id: id}, function(result) {
                $scope.skill = result;
            });
        };
        var unsubscribe = $rootScope.$on('ontimeApp:skillUpdate', function(event, result) {
            $scope.skill = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
