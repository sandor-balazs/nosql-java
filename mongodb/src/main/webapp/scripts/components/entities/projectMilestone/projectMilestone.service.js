'use strict';

angular.module('ontimeApp')
    .factory('ProjectMilestone', function ($resource, DateUtils) {
        return $resource('api/projectMilestones/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.planned = DateUtils.convertLocaleDateFromServer(data.planned);
                    data.actual = DateUtils.convertLocaleDateFromServer(data.actual);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.planned = DateUtils.convertLocaleDateToServer(data.planned);
                    data.actual = DateUtils.convertLocaleDateToServer(data.actual);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.planned = DateUtils.convertLocaleDateToServer(data.planned);
                    data.actual = DateUtils.convertLocaleDateToServer(data.actual);
                    return angular.toJson(data);
                }
            }
        });
    });
