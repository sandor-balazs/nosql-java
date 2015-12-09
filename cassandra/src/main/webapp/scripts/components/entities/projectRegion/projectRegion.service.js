'use strict';

angular.module('ontimeApp')
    .factory('ProjectRegion', function ($resource, DateUtils) {
        return $resource('api/projectRegions/:id', {}, {
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
