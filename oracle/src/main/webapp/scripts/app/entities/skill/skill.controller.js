'use strict';

angular.module('ontimeApp')
    .controller('SkillController', function ($scope, $state, Skill) {

        $scope.skills = [];
        $scope.loadAll = function() {
            Skill.query(function(result) {
               $scope.skills = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.skill = {
                name: null,
                id: null
            };
        };
    });
