'use strict';

angular.module('ontimeApp')
    .factory('Skill', function ($resource, DateUtils) {
        return $resource('api/skills/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
